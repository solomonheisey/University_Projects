from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()


class User(db.Model):
    user_id = db.Column(db.Integer, unique=True, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    password = db.Column(db.String(64), nullable=False)
    purchases = db.relationship('Purchase', backref='user', lazy=True)
    categories = db.relationship('Category', backref='user', lazy=True)

    def __init__(self, username, password):
        self.username = username
        self.password = password


class Category(db.Model):
    category_id = db.Column(db.Integer, unique=True, primary_key=True)
    category_name = db.Column(db.String(80), nullable=False)
    limit = db.Column(db.Float, nullable=False)
    purchases = db.relationship('Purchase', backref='category', lazy=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.user_id'))

    def __init__(self, category_name, limit, user_id):
        self.category_name = category_name
        self.limit = limit
        self.user_id = user_id


class Purchase(db.Model):
    purchase_id = db.Column(db.Integer, unique=True, primary_key=True)
    amount = db.Column(db.Float, nullable=False)
    description = db.Column(db.String(80))
    date = db.Column(db.DateTime, nullable=False)
    category_id = db.Column(db.Integer, db.ForeignKey('category.category_id'), nullable=False)
    user_id = db.Column(db.Integer, db.ForeignKey('user.user_id'), nullable=False)

    def __init__(self, amount, description, date, category_id, user_id):
        self.amount = amount
        self.description = description
        self.date = date
        self.category_id = category_id
        self.user_id = user_id
