import json

from flask import Flask, render_template, redirect, url_for, g, request, flash, session, abort

app = Flask(__name__)

PER_PAGE = 30
DEBUG = True
SECRET_KEY = 'development key'

app.config.from_object(__name__)
app.config.from_envvar('EVENTIFY_SETTINGS', silent=True)

users = {}
chatRooms = {}
chatMessages = {"test": [["solomon", "hello this is a message"],
                         ["bob", "this is another message"]]}


@app.before_request
def before_request():
    g.user = None
    if 'username' in session:
        g.user = session['username']


@app.route('/')
def homepage():
    return render_template('home.html')


@app.route('/register', methods=['GET', 'POST'])
def register():
    error = None
    if request.method == 'POST':
        if not request.form['username']:
            error = 'You must enter a username'
        elif not request.form['password']:
            error = 'You muse enter a password'
        elif request.form['passwordRepeat'] != request.form['password']:
            error = 'Passwords do not match'
        elif users.get(request.form['username']) is not None:
            error = 'Username has been taken'
        else:
            users[request.form['username']] = request.form['password']
            flash('You have been successfully registered for PingPong')
            return redirect(url_for('login'))
    return render_template('register.html', error=error)


currUser = None


@app.route('/login', methods=['POST', 'GET'])
def login():
    global currUser
    error = None
    if request.method == 'POST':
        if users.get(request.form['username']) != request.form['password']:
            error = 'Invalid Login'
        else:
            flash('You have been logged in')
            session['username'] = request.form['username']
            currUser = request.form['username']
            return redirect(url_for('messages'))
    return render_template('login.html', error=error)


@app.route('/messages')
def messages():
    return render_template('chatRooms.html', rooms=chatRooms)


@app.route('/create_chat_room', methods=['POST', 'GET'])
def create_chat_room():
    if 'username' not in session:
        abort(401)

    error = None
    if request.form['name'] in chatRooms:
        error = "Chat Room Already Exists"
    else:
        # chat room name is stored along with user who created it
        chatRooms[request.form['name']] = session['username']
        flash('Chat room has been created')
    return render_template('chatRooms.html', error=error, rooms=chatRooms)


lastSession = None


@app.route('/<r>/join_room')
def join_room(r):
    global lastSession
    if 'currRoom' in session:
        print(session['currRoom'])
        if session['currRoom'] is not None:
            flash('You cannot join another room since you are currently in ' + session['currRoom'])
            return redirect(url_for('messages'))

    session['currRoom'] = r
    lastSession = r
    flash("You have joined " + r)
    authors = []
    currentMessages = []
    for room, message in chatMessages.items():
        if room == r:
            print(room + " " + str(message))
            for author, m in message:
                authors.append(author)
                currentMessages.append(m)

    return render_template('currentChat.html', r=r, a=authors, m=currentMessages)


@app.route('/<r>/delete_room')
def delete_room(r):
    flash(r + ' has been deleted')
    del chatMessages[r]
    return redirect(url_for('messages'))


@app.route("/new_message", methods=["POST"])
def new_message():
    if session['currRoom'] in chatMessages.keys():
        chatMessages[session['currRoom']].append([session['username'], request.form['message']])
    else:
        chatMessages[session['currRoom']] = [[session['username'], request.form['message']]]
    return "OK!"


@app.route('/logout')
def logout():
    global lastSession
    global currUser
    flash('You have been logged out')
    session.pop('username', None)
    session.pop('currRoom', None)
    lastSession = None
    currUser = None
    return redirect(url_for('homepage'))


@app.route('/<r>/leave')
def leave(r):
    global lastSession
    flash('You have left ' + r)
    session.pop('currRoom', None)
    lastSession = None
    return redirect(url_for('messages'))


@app.route('/messages_poll')
def get_messages():
    global lastSession
    print(json.dumps(chatMessages))
    if len(json.dumps(chatMessages)) == 0:
        flash('Room has been deleted')
        session.pop('currRoom', None)
        lastSession = None
        return redirect(url_for('messages'))

    return json.dumps(chatMessages)


if __name__ == "__main__":
    app.run()
