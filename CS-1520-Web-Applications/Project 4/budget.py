import os
from datetime import datetime

from flask import Flask, session, g, render_template, request, flash, redirect, url_for, abort, jsonify
from werkzeug.security import check_password_hash, generate_password_hash
from flask_restful import Api, Resource, reqparse

from models import db, User, Category, Purchase

app = Flask(__name__)
api = Api(app)

SECRET_KEY = 'development key'

SQLALCHEMY_DATABASE_URI = 'sqlite:///' + os.path.join(app.root_path, 'budget.db')

app.config.from_object(__name__)
app.config.from_envvar('BUDGET_SETTINGS', silent=True)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db.init_app(app)

parser = reqparse.RequestParser()
parser.add_argument('cat')
parser.add_argument('limit')
parser.add_argument('cost')
parser.add_argument('description')
parser.add_argument('date')
parser.add_argument('category')


def check_category_id(category_id):
    category_id = Category.query.filter_by(category_id=category_id).first()
    if category_id is None:
        abort(404, message="Category {} doesn't exist".format(category_id))


class Cat(Resource):
    def delete(self, category_id):
        check_category_id(category_id)

        purchases = Purchase.query.filter_by(category_id=category_id, user_id=session['user_id'])

        uncategorized = Category.query.filter_by(category_name="Uncategorized", user_id=session['user_id']).first()

        todayMonth = datetime.today().date().month
        todayYear = datetime.today().date().year

        for p in purchases:
            comparisonMonth = datetime.date(p.date).month
            comparisonYear = datetime.date(p.date).year
            p.category_id = 1

            if comparisonMonth == todayMonth and comparisonYear == todayYear:
                uncategorized.limit += p.amount

        Category.query.filter_by(category_id=category_id, user_id=session['user_id']).delete()
        db.session.commit()
        return '', 204


class Cats(Resource):
    def get(self):
        cols = ['category_name', 'limit', 'category_id']
        data = Category.query.filter_by(user_id=session['user_id']).all()
        result = [{col: getattr(d, col) for col in cols} for d in data]
        return jsonify(result=result)

    def post(self):
        args = parser.parse_args()
        category = {'cat': args['cat']}
        category = str(category['cat'])

        if category is None:
            abort(400, 'Category must be present')

        spending_limit = {'limit': args['limit']}
        try:
            spending_limit = float(spending_limit['limit'])
        except ValueError:
            abort(400, 'Spending limit must be present')

        temp = Category.query.filter_by(category_name=category, user_id=session['user_id']).first()
        if temp is not None:
            abort(400, 'Category name already exists')

        db.session.add(Category(category, spending_limit, session['user_id']))
        db.session.commit()
        return (category, spending_limit), 201


class Purchases(Resource):
    def get(self):
        cols = ['amount', 'description', 'date', 'category_id']
        data = Purchase.query.filter_by(user_id=session['user_id']).all()
        result = [{col: getattr(d, col) for col in cols} for d in data]
        return jsonify(result=result)

    def post(self):
        args = parser.parse_args()
        cost = {'cost': args['cost']}

        try:
            cost = float(cost['cost'])
        except ValueError:
            abort(400, 'Cost must be present')

        description = {'description': args['description']}
        description = str(description['description'])

        date = {'date': args['date']}
        date = str(date['date'])
        if date is None:
            abort(400, 'Date must be present')
        formatted_date = datetime.strptime(date, '%Y-%m-%dT%H:%M')
        comparisonDateMonth = formatted_date.date().month
        comparisonDateYear = formatted_date.date().year
        todayMonth = datetime.today().date().month
        todayYear = datetime.today().date().year

        categoryOriginal = {'category': args['category']}
        category = str(categoryOriginal['category'])

        try:
            category
        except NameError:
            var_exists = False
        else:
            var_exists = True

        if var_exists and category and category is not "Uncategorized":
            category_id = Category.query.filter_by(category_name=category,
                                                   user_id=session['user_id']).first().category_id
            if category_id is None:
                abort(400, 'Category name does not exist')
        else:
            category_id = Category.query.filter_by(category_name="Uncategorized",
                                                   user_id=session['user_id']).first().category_id

        data = Category.query.all()
        for d2 in data:
            if category_id == d2.category_id and (
                    comparisonDateYear == todayYear and comparisonDateMonth == todayMonth):
                if category_id == 1:
                    d2.limit += cost
                else:
                    d2.limit -= cost

        db.session.add(Purchase(cost, description, formatted_date, category_id, session['user_id']))
        db.session.commit()
        return (cost, description, date, category_id), 201


api.add_resource(Cat, '/cats/<category_id>')
api.add_resource(Cats, '/cats')
api.add_resource(Purchases, '/purchases')


@app.cli.command('initdb')
def initdb_command():
    db.create_all()
    print("DB has been initialized")


@app.before_request
def before_request():
    g.user = None
    if 'user_id' in session:
        g.user = User.query.filter_by(user_id=session['user_id']).first().username


@app.route('/')
def homepage():
    return render_template('home.html')


@app.route('/login', methods=['POST', 'GET'])
def login():
    error = None
    if request.method == 'POST':
        user = User.query.filter_by(username=request.form['username']).first()
        if user is None or not check_password_hash(user.password, request.form['password']):
            error = 'Invalid Login'
        else:
            flash('You have been logged in')
            session['user_id'] = user.user_id
            session['username'] = user.username
            return redirect(url_for('skeleton'))
    return render_template('login.html', error=error)


@app.route('/skeleton')
def skeleton():
    temp = Category.query.filter_by(category_name='Uncategorized', user_id=session['user_id']).first()
    if temp is None:
        db.session.add(Category(category_name='Uncategorized', limit=0, user_id=session['user_id']))
        db.session.commit()

    return render_template('skeleton.html')


@app.route('/register', methods=['POST', 'GET'])
def register():
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
            flash('You have be successfully be registered for Budget')
            return redirect(url_for('login'))
    return render_template('register.html', error=error)


@app.route('/logout')
def logout():
    flash('You have been logged out')
    session.pop('user_id', None)
    session.pop('username', None)
    return redirect(url_for('homepage'))


def get_user_id(username):
    rv = User.query.filter_by(username=username).first()
    return rv.user_id if rv else None
