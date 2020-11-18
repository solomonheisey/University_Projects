--------------------------------------------------------------------------
--- Schema of Olympic Games Project Author: Solomon Heisey ---------------
--------------------------------------------------------------------------

-- role_id is chosen as PK and role_name cannot be null since it is an enumerated data type, roles can be Organizer,
-- Coach, and Guest
CREATE TABLE USER_ROLE
(
    role_id     integer      not null,
    role_name   varchar2(20) not null,
    CONSTRAINT USER_ROLE_PK PRIMARY KEY ( role_id ),
    CONSTRAINT USER_ROLE_ID_CHECK CHECK (role_id >= 1 and role_id < 4),
    CONSTRAINT USER_ROLE_NAME CHECK ( role_name IN ('Organizer','Coach','Guest'))
);

-- user_id is chosen as PK since username can change, but user_id cannot
-- role_id cannot be null since it is an enumerated data type, roles are either Organizer, Coach, or Guest, a passkey must
-- be present for security, and a username but be present and unique
CREATE TABLE USER_ACCOUNT
(
    user_id    integer      not null,
    username   varchar2(20) not null,
    passkey    varchar2(20) not null,
    role_id     integer     not null,
    last_login  date        not null,
    CONSTRAINT USER_ACCOUNT_PK PRIMARY KEY ( user_id ),
    CONSTRAINT USER_ACCOUNT_FK1 FOREIGN KEY ( role_id ) REFERENCES USER_ROLE ( role_id ),
    CONSTRAINT USER_ACCOUNT_UN UNIQUE ( username )
);

-- olympic_id is chosen as PK and olympic_num must be unique since it identifies which Olympic games (i.e. V,VI, VII)
CREATE TABLE OLYMPICS
(
    olympic_id          integer         not null,
    olympic_num         varchar2(30)    not null,
    host_city           varchar2(30)    not null,
    opening_date        date            not null,
    closing_date        date            not null,
    official_website    varchar2(50),
    CONSTRAINT OLYMPICS_PK PRIMARY KEY ( olympic_id ),
    CONSTRAINT OLYMPICS_UN UNIQUE ( olympic_num ),
    CONSTRAINT OLYMPICS_UN1 UNIQUE ( host_city, opening_date)
);

-- sport_id is chosen as PK, sport_name cannot be null since every sport entered must have a name. Also, the sport must
-- have a dob since it is in the Olympics, lastly since there must be 1 or more people on each team team_size is not null
CREATE TABLE SPORT
(
    sport_id    integer         not null,
    sport_name  varchar2(30)    not null,
    description varchar2(80),
    dob         date            not null,
    team_size   integer         not null,
    CONSTRAINT SPORT_PK PRIMARY KEY ( sport_id ),
    CONSTRAINT SPORT_UN UNIQUE ( sport_name ),
    CONSTRAINT SPORT_SIZE CHECK ( team_size >= 1 AND team_size IS NOT NULL)
);

-- country_id is chosen as PK. Since each associated country has a code and a name, they can't be null and must be unique to eliminate
-- duplicates
CREATE TABLE COUNTRY
(
    country_id      integer      not null,
    country         varchar2(20) not null,
    country_code    varchar2(3)  not null,
    CONSTRAINT COUNTRY_PK PRIMARY KEY ( country_id ),
    CONSTRAINT COUNTRY_UQ1 UNIQUE ( country_code ),
    CONSTRAINT COUNTRY_UQ2 UNIQUE ( country )
);

-- participant_id is chosen as PK
CREATE TABLE PARTICIPANT
(
    participant_id  integer         not null,
    fname           varchar2(30)    not null,
    lname           varchar2(30)    not null,
    nationality     varchar2(20)    not null,
    birth_place     varchar2(40)    not null,
    dob             date            not null,
    CONSTRAINT PARTICIPANT_PK PRIMARY KEY ( participant_id ),
    CONSTRAINT PARTICIPANT_FK FOREIGN KEY ( nationality ) REFERENCES COUNTRY (country)
);

-- team_id is chosen as PK. Since each team is participating in the olympics it is assumed that they have an olympic_id,
-- a team_name, a country_id, a sport_id
CREATE TABLE TEAM
(
    team_id     integer         not null,
    olympics_id integer         not null,
    team_name   varchar2(50)    not null,
    country_id  integer         not null,
    sport_id    integer         not null,
    coach_id    integer,
    CONSTRAINT TEAM_PK PRIMARY KEY ( team_id ),
    CONSTRAINT TEAM_FK1 FOREIGN KEY ( country_id ) REFERENCES COUNTRY ( country_id ),
    CONSTRAINT TEAM_FK2 FOREIGN KEY ( olympics_id ) REFERENCES OLYMPICS ( olympic_id ),
    CONSTRAINT TEAM_FK3 FOREIGN KEY ( sport_id ) REFERENCES SPORT ( sport_id ),
    CONSTRAINT TEAM_FK4 FOREIGN KEY ( coach_id ) REFERENCES PARTICIPANT ( participant_id ) DEFERRABLE
);

-- (team_id, participant_id) are selected as PK since team_id identifies what team each participant is on, but
-- put together with participant_id  we are able to distinguish people belonging to the same team
CREATE TABLE TEAM_MEMBER
(
    team_id           integer   not null,
    participant_id    integer   not null,
    CONSTRAINT TEAM_MEMBER_PK PRIMARY KEY ( team_id, participant_id ),
    CONSTRAINT TEAM_MEMBER_FK1 FOREIGN KEY ( team_id ) REFERENCES TEAM ( team_id ) DEFERRABLE,
    CONSTRAINT TEAM_MEMBER_FK2 FOREIGN KEY ( participant_id ) REFERENCES PARTICIPANT ( participant_id ) DEFERRABLE
);

-- medal_id is selected as PK. Since medal_title is enumerated there is not reason why medal_title would be null and the
-- same logic also applies for points
CREATE TABLE MEDAL
(
    medal_id    integer     not null,
    medal_title varchar2(6) not null,
    points      integer     not null,
    CONSTRAINT MEDAL_PK PRIMARY KEY ( medal_id ),
    CONSTRAINT MEDAL_TYPE CHECK ( medal_title = 'gold' OR medal_title = 'silver' OR medal_title = 'bronze' OR medal_title = 'none')
);

-- (venue_id, olympics_id) is selected as the PK since only one venue can be set for the games
CREATE TABLE VENUE
(
    venue_id    integer         not null,
    olympics_id integer         not null,
    venue_name  varchar2(30)    not null,
    capacity    integer         not null,
    CONSTRAINT VENUE_PK PRIMARY KEY ( venue_id ),
    CONSTRAINT VENUE_FK FOREIGN KEY ( olympics_id ) REFERENCES OLYMPICS ( olympic_id ),
    CONSTRAINT VENUE_UN UNIQUE ( olympics_id, venue_id ),
    CONSTRAINT VENUE_CAPACITY_CHECK CHECK ( capacity >= 0)
);

-- event_id is selected as the PK and each event must have an event_id, a sport, a venue, and a gender (1 for mens 0 for womens)
CREATE TABLE EVENT
(
    event_id    integer     not null,
    sport_id    integer     not null,
    venue_id    integer     not null,
    gender      integer     not null,
    event_time  date        not null,
    CONSTRAINT EVENT_PK PRIMARY KEY ( event_id ),
    CONSTRAINT EVENT_FK1 FOREIGN KEY ( sport_id ) REFERENCES SPORT ( sport_id ),
    CONSTRAINT EVENT_FK2 FOREIGN KEY ( venue_id ) REFERENCES VENUE ( venue_id ),
    CONSTRAINT EVENT_GENDER CHECK ( gender = 1 OR gender = 0 )
);

-- ( olympics_id, event_id, team_id, participant_id ) is the PK since the same participant cannot participate multiple times
-- in the same event on the same team. Also, a participant will place but may not get a medal
CREATE TABLE SCOREBOARD
(
    olympics_id     integer     not null,
    event_id        integer     not null,
    team_id         integer     not null,
    participant_id  integer     not null,
    position        integer     not null,
    medal_id        integer     not null,
    CONSTRAINT SCOREBOARD_PK PRIMARY KEY ( olympics_id, event_id, team_id, participant_id ),
    CONSTRAINT SCOREBOARD_FK1 FOREIGN KEY ( olympics_id ) REFERENCES OLYMPICS ( olympic_id ),
    CONSTRAINT SCOREBOARD_FK2 FOREIGN KEY ( event_id ) REFERENCES EVENT ( event_id ),
    CONSTRAINT SCOREBOARD_FK3 FOREIGN KEY ( team_id, participant_id ) REFERENCES TEAM_MEMBER ( team_id, participant_id ) DEFERRABLE,
    CONSTRAINT SCOREBOARD_FK5 FOREIGN KEY ( medal_id ) REFERENCES MEDAL ( medal_id )
);

-- (event_id, team_id) is selected as PK since a team can participate multiple times, but just not in the same event, also
-- status must be e or n
CREATE TABLE EVENT_PARTICIPATION
(
    event_id    integer     not null,
    team_id     integer     not null,
    status      char        not null,
    CONSTRAINT EVENT_PARTICIPATION_PK PRIMARY KEY ( event_id, team_id ),
    CONSTRAINT EVENT_PARTICIPATION_FK1 FOREIGN KEY ( event_id ) REFERENCES EVENT ( event_id ),
    CONSTRAINT EVENT_PARTICIPATION_FK2 FOREIGN KEY ( team_id ) REFERENCES TEAM ( team_id ) DEFERRABLE,
    CONSTRAINT EVENT_STATUS CHECK ( status = 'e' OR status = 'n' )
);

PURGE RECYCLEBIN;
COMMIT;