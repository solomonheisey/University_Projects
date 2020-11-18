from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

attend_events = db.Table('attend_events',
                         db.Column('user_id', db.Integer, db.ForeignKey('user.user_id')),
                         db.Column('event_id', db.Integer, db.ForeignKey('event.event_id')))


class User(db.Model):
    user_id = db.Column(db.Integer, unique=True, primary_key=True)
    username = db.Column(db.String(80), unique=True, nullable=False)
    password = db.Column(db.String(64), nullable=False)
    host_events = db.relationship('Event', backref='host')
    attend_events = db.relationship('Event', secondary='attend_events', backref=db.backref('User', lazy='dynamic'))

    def __init__(self, username, password):
        self.username = username
        self.password = password


class Event(db.Model):
    event_id = db.Column(db.Integer, unique=True, primary_key=True)
    title = db.Column(db.String(256), nullable=False)
    description = db.Column(db.String(256))
    start = db.Column(db.DateTime, nullable=False)
    end = db.Column(db.DateTime, nullable=False)
    host_username = db.Column(db.String(80), db.ForeignKey('user.username'), nullable=False)

    def __init__(self, title, description, start, end, host_username):
        self.title = title
        self.description = description
        self.start = start
        self.end = end
        self.host_username = host_username
