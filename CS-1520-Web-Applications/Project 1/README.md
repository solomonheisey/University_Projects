## Overview

In this project, you will be creating an event hosting website. Users will be able to sign up for your site, login and logout. Users will also be able to host events and register to attend events.
Required Packages

    Python 3.x
    Flask
    Jinja2 Templating engine (included with Flask)
    Flask-SQLAlchemy

## Specification

Below are the specifications for the application. I have split the specifications up based on MVC. Please read through the entired specification and submission guidlines before you start development.
# Model

    User, each User must have:
        username
            each User must have a unique username
            blank usernames are not allowed
        password
            passwords must be hashed before being saved to the database
            blank passwords are not allowed
    Event, each Event must have:
        title
            blank titles are not allowed
        description
        start date & time
            blank starts are not allowed
        end date & time
            blank ends are not allowed
    Relationships between User and Event
        User can host many Events
        User can attend many Events and each Event will have many Users attending
    Note: You are not limited to the columns listed above. Add additional columns and association table as needed to facilitate the relationships between User and Event.

## Controller

    You will need a homepage that displays:
        a list of upcoming events hosted by all users
        if a user is logged in, display a separate list of upcoming events hosted by the logged in user
        for both lists above, list the events in order of their start date & time
    The User will need routes/functions defined to:
        register for the website
        login to the website
        logout of the website
    The Event will need routes/functions defined to allow the user to:
        create an event with the logged in user as host
        cancel an event
            a user cannot cancel an event they are not hosting
            when a user tries to cancel an event, they must be asked to confirm that they want to cancel the event before it is removed from the database
        register to attend an event
            a user should not be allowed to sign up to attend an event they are hosting
            you do not need to allow users to unregister for an event in this project

## Views (Templates)

    A base template must be created and extended by all other templates
    Templates need to be created for the following routes:
        the homepage
        user registration
        user login
        event creation
        event cancellation (to act as the confirmation since JavaScript is not part of this project)
    The main menu for the site must show different links depending on if the user is logged in.
    Events on the homepage must list:
        title
        host
        description
        start date & time
        end date & time
        a link to allow a user to register to attend an event if the user is not the host of the event
        a cancel event link if the user is the host of the event
    Design and CSS is left up to you. The only design requirement is that the page is presented in a clear and readable manner.
