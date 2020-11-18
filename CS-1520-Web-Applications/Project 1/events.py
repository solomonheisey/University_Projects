from datetime import datetime
import os
from flask import Flask, g, redirect, url_for, session, render_template, flash, request, abort
from werkzeug.security import check_password_hash, generate_password_hash

from models import db, User, attend_events, Event

app = Flask(__name__)

PER_PAGE = 30
DEBUG = True
SECRET_KEY = 'development key'

SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(app.root_path, 'events.db')

app.config.from_object(__name__)
app.config.from_envvar('EVENTIFY_SETTINGS', silent=True)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db.init_app(app)


@app.cli.command('initdb')
def initdb_command():
    db.create_all()
    print("DB has been initialized")


@app.before_request
def before_request():
    g.user = None
    if 'user_id' in session:
        g.user = User.query.filter_by(user_id=session['user_id']).first()


@app.route('/')
def get_users_events():
    if not g.user:
        return redirect(url_for('public_page'))
    u = User.query.filter_by(user_id=session['user_id']).first()

    # gets events hosted by user
    event_ids = []
    for event in u.host_events:
        event_ids.append(event.event_id)
    ev = Event.query.filter(Event.event_id.in_(event_ids)).order_by(Event.start.desc()).limit(PER_PAGE).all()

    public = Event.query.filter(~Event.event_id.in_(event_ids)).order_by(Event.start.desc()).limit(PER_PAGE).all()

    return render_template('events.html', events=ev, public=public)


@app.route('/home')
def public_page():
    return render_template('events.html', events=Event.query.order_by(Event.start.desc()).limit(PER_PAGE).all())


@app.route('/logout')
def logout():
    flash('You have been logged out')
    session.pop('user_id', None)
    session.pop('username', None)
    return redirect(url_for('public_page'))


@app.route('/login', methods=['GET', 'POST'])
def login():
    if g.user:
        return redirect(url_for('get_users_events'))
    error = None
    if request.method == 'POST':
        user = User.query.filter_by(username=request.form['username']).first()
        if user is None or not check_password_hash(user.password, request.form['password']):
            error = 'Invalid Login'
        else:
            flash('You have been logged in')
            session['user_id'] = user.user_id
            session['username'] = user.username
            return redirect(url_for('get_users_events'))
    return render_template('login.html', error=error)


@app.route('/create_event', methods=['POST'])
def create_event():
    if 'user_id' not in session:
        abort(401)

    request_form_title = request.form['title']
    request_form_description = request.form['description']
    request_form_start = request.form['start']
    request_form_end = request.form['end']

    start = datetime.strptime(request_form_start, '%Y-%m-%dT%H:%M')
    end = datetime.strptime(request_form_end, '%Y-%m-%dT%H:%M')

    temp = User.query.filter_by(user_id=session['user_id']).first()

    db.session.add(Event(title=request_form_title, description=request_form_description, start=start,
                         end=end, host_username=temp.username))
    db.session.commit()

    flash('Your event has been created')
    return redirect(url_for('get_users_events'))


def get_user_id(username):
    rv = User.query.filter_by(username=username).first()
    return rv.user_id if rv else None


@app.route('/register', methods=['GET', 'POST'])
def register():
    if g.user:
        return redirect(url_for('get_users_events'))
    error = None
    if request.method == 'POST':
        if not request.form['username']:
            error = 'You must enter a username'
        elif not request.form['password']:
            error = 'You must enter a password'
        elif request.form['passwordRepeat'] != request.form['password']:
            error = 'Passwords do not match'
        elif get_user_id(request.form['username']) is not None:
            error = 'Username has been taken'
        else:
            db.session.add(User(request.form['username'], generate_password_hash(request.form['password'])))
            db.session.commit()
            flash('You have be successfully be registered for Hostify')
            return redirect(url_for('login'))
    return render_template('register.html', error=error)


@app.route('/<c>/<e_id>/cancel/confirm')
def cancel_event_confirm(c, e_id):
    if int(c) == 0:
        flash('Your event has not been canceled')
        return redirect(url_for('get_users_events'))
    else:
        Event.query.filter_by(event_id=int(e_id)).delete()
        db.session.commit()

        flash('Your event has been canceled')

        return redirect(url_for('get_users_events'))


@app.route('/<e>/cancel')
def cancel_event(e):
    if not g.user:
        abort(401)
    if e is None:
        abort(401)

    # if not current user tries to cancel event
    temp = Event.query.filter_by(event_id=e).first()
    if temp.host_username != session['username']:
        abort(401)

    e_id = temp.event_id
    return render_template('confirmation.html', e=temp, c=0, e_id=e_id)


@app.route('/<e>/attend')
def attend_event(e):
    event = Event.query.get(int(e))

    if event in User.query.get(session['user_id']).attend_events:
        flash('You have already been signed up for this event')
    else:
        User.query.get(session['user_id']).attend_events.append(event)
        db.session.commit()
        flash('You have been signed up for this event')

    return redirect(url_for('get_users_events'))
