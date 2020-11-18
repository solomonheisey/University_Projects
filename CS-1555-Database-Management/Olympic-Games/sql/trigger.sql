--------------------------------------------------------------------------
--- DB Objects of Olympic Games Project Author: Solomon Heisey -----------
--------------------------------------------------------------------------

CREATE SEQUENCE user_seq START WITH 13;
CREATE SEQUENCE participant_seq START WITH 20011;
CREATE SEQUENCE team_seq START WITH 2011;
CREATE SEQUENCE event_seq START WITH 2004;

CREATE OR REPLACE TRIGGER ASSIGN_MEDAL
    BEFORE INSERT OR UPDATE ON SCOREBOARD
    REFERENCING NEW AS new
    OLD AS old
    FOR EACH ROW
    DECLARE
        medal SCOREBOARD.medal_id%TYPE;
        pragma autonomous_transaction;
    BEGIN
        IF (:new.position = 1) THEN
            medal := 1;
        ELSIF (:new.position = 2) THEN
            medal := 2;
        ELSIF (:new.position = 3) THEN
            medal := 3;
        ELSE
            medal := 4;
        end if;
        :new.medal_id := medal;
    END;

CREATE OR REPLACE TRIGGER ATHLETE_DISMISSAL
    BEFORE DELETE ON PARTICIPANT
    REFERENCING OLD AS old
    FOR EACH ROW
    DECLARE
        curr_team_size SPORT.team_size%TYPE;
        curr_team TEAM.team_id%TYPE;
        curr_participant PARTICIPANT.participant_id%TYPE;
        pragma autonomous_transaction ;
    BEGIN
        execute immediate 'SET CONSTRAINTS ALL DEFERRED';

        curr_participant := :old.participant_id;

        SELECT team_size, TEAM.team_id
        INTO curr_team_size, curr_team
        FROM
             PARTICIPANT INNER JOIN TEAM_MEMBER ON PARTICIPANT.PARTICIPANT_ID = TEAM_MEMBER.PARTICIPANT_ID
             INNER JOIN TEAM ON TEAM_MEMBER.TEAM_ID = TEAM.TEAM_ID
             INNER JOIN SPORT ON TEAM.SPORT_ID = SPORT.SPORT_ID
        WHERE PARTICIPANT.PARTICIPANT_ID = curr_participant;

        IF(curr_team_size = 1) THEN
            DELETE FROM TEAM_MEMBER WHERE PARTICIPANT_ID = curr_participant AND TEAM_ID = curr_team;
            DELETE FROM SCOREBOARD WHERE PARTICIPANT_ID = curr_participant AND TEAM_ID = curr_team;
            DELETE FROM TEAM WHERE TEAM_ID = curr_team;
            DELETE FROM EVENT_PARTICIPATION WHERE TEAM_ID = curr_team;
        ELSE
            DELETE FROM TEAM_MEMBER WHERE PARTICIPANT_ID = curr_participant AND TEAM_ID = curr_team;
            DELETE FROM SCOREBOARD WHERE PARTICIPANT_ID = curr_participant AND TEAM_ID = curr_team;
            UPDATE EVENT_PARTICIPATION SET STATUS = 'n' WHERE TEAM_ID = curr_team;
         end if;
        commit;
    END;

CREATE OR REPLACE TRIGGER participant_seqID
    BEFORE INSERT ON PARTICIPANT
    FOR EACH ROW
    BEGIN
        SELECT PARTICIPANT_SEQ.nextval
        INTO :NEW.participant_id
        FROM DUAL;
    END;

    CREATE OR REPLACE TRIGGER team_seqID
    BEFORE INSERT ON TEAM
    FOR EACH ROW
    BEGIN
        SELECT TEAM_SEQ.nextval
        INTO :NEW.team_ID
        FROM DUAL;
    END;

CREATE VIEW GET_FIRST_OLYMPICS AS
    SELECT
        COUNTRY_ID,
        min(extract(year from opening_date)) as dob
    FROM
        SCOREBOARD S INNER JOIN TEAM_MEMBER T on S.TEAM_ID = T.TEAM_ID and S.PARTICIPANT_ID = T.PARTICIPANT_ID
        INNER JOIN TEAM TE on T.TEAM_ID = TE.TEAM_ID
        INNER JOIN OLYMPICS O on S.OLYMPICS_ID = O.OLYMPIC_ID
    GROUP BY
        country_id;

CREATE OR REPLACE PROCEDURE PROC_GET_OLYMPIC(input_olympic_id IN OLYMPICS.olympic_id%TYPE, cursor_ OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN cursor_ FOR
            SELECT
                DENSE_RANK() OVER(ORDER BY SUM(points) DESC) as rank,
                COUNTRY_CODE,
                (select dob from GET_FIRST_OLYMPICS where country_id = c.COUNTRY_ID) as dob,
                count(case when M.medal_id = 1 then 1 end) as gold,
                count(case when M.medal_id = 2 then 1 end) as silver,
                count(case when M.MEDAL_ID = 3 then 1 end) as bronze,
                sum(points) as total_points
            FROM
                SCOREBOARD S INNER JOIN OLYMPICS O ON S.OLYMPICS_ID = O.OLYMPIC_ID
                INNER JOIN MEDAL M ON S.MEDAL_ID = M.MEDAL_ID
                INNER JOIN TEAM_MEMBER T ON S.TEAM_ID = T.TEAM_ID and S.PARTICIPANT_ID = T.PARTICIPANT_ID
                INNER JOIN TEAM TE ON T.TEAM_ID = TE.TEAM_ID
                INNER JOIN COUNTRY C ON TE.COUNTRY_ID = C.COUNTRY_ID
            WHERE
                OLYMPIC_ID = input_olympic_id
            GROUP BY
                COUNTRY_CODE, C.COUNTRY_ID;
    END;

CREATE OR REPLACE PROCEDURE PROC_GET_EVENTS(input_sport_id IN SPORT.sport_id%TYPE, cursor_ OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN cursor_ FOR
            SELECT
                EVENT_ID,
                GENDER,
                full_name,
                country
            FROM(
                SELECT DISTINCT
                    E.event_id,
                    gender,
                    S2.participant_id,
                    FNAME || ' ' || lname as full_name,
                    S2.MEDAL_ID,
                    medal_title,
                    country,
                    extract(YEAR FROM opening_date) AS year
                FROM
                    EVENT E INNER JOIN SPORT S on E.SPORT_ID = S.SPORT_ID
                    INNER JOIN SCOREBOARD S2 on E.EVENT_ID = S2.EVENT_ID
                    INNER JOIN OLYMPICS O on S2.OLYMPICS_ID = O.OLYMPIC_ID
                    INNER JOIN TEAM_MEMBER T on S2.TEAM_ID = T.TEAM_ID and S2.PARTICIPANT_ID = T.PARTICIPANT_ID
                    INNER JOIN TEAM T2 ON T.TEAM_ID = T2.TEAM_ID
                    INNER JOIN COUNTRY C ON T2.COUNTRY_ID = C.COUNTRY_ID
                    INNER JOIN MEDAL M on S2.MEDAL_ID = M.MEDAL_ID
                    INNER JOIN EVENT_PARTICIPATION EP on E.EVENT_ID = EP.EVENT_ID
                    INNER JOIN PARTICIPANT P on T.PARTICIPANT_ID = P.PARTICIPANT_ID
                WHERE
                    E.SPORT_ID = input_sport_id AND T2.SPORT_ID = input_sport_id AND S2.MEDAL_ID < 4 AND status = 'e'
                ORDER BY
                    S2.MEDAL_ID, year DESC);
    END;

CREATE OR REPLACE PROCEDURE PROC_GET_PARTICIPANTS(input_olympic_id IN OLYMPICS.olympic_id%TYPE, input_event_id IN EVENT.event_id%TYPE, cursor_ OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN cursor_ FOR
            SELECT
                host_city || ' ' || EXTRACT(YEAR FROM opening_date) AS olympic_game,
                sport_name || ' ' || e.EVENT_ID AS event_name,
                fname || ' ' || lname AS full_name,
                position,
                MEDAL_TITLE
            FROM OLYMPICS O INNER JOIN SCOREBOARD S on O.OLYMPIC_ID = S.OLYMPICS_ID
            INNER JOIN EVENT E on S.EVENT_ID = E.EVENT_ID
            INNER JOIN SPORT S2 on E.SPORT_ID = S2.SPORT_ID
            INNER JOIN PARTICIPANT P on S.PARTICIPANT_ID = P.PARTICIPANT_ID
            INNER JOIN MEDAL M on S.MEDAL_ID = M.MEDAL_ID
            WHERE OLYMPIC_ID = input_olympic_id AND E.EVENT_ID = input_event_id;
    END;

CREATE OR REPLACE PROCEDURE PROC_GET_ATHLETES(input_olympic_id IN OLYMPICS.olympic_id%TYPE, k IN number, cursor_ OUT SYS_REFCURSOR) AS
    BEGIN
        OPEN cursor_ FOR
            SELECT * FROM (
                SELECT
                    DENSE_RANK() OVER(ORDER BY SUM(points) DESC) as rank,
                    fname || ' ' || lname as full_name,
                    P.participant_id,
                    count(case when M.medal_id = 1 then 1 end) as gold,
                    count(case when M.medal_id = 2 then 1 end) as silver,
                    count(case when M.MEDAL_ID = 3 then 1 end) as bronze,
                    SUM(points) as total_points
                FROM
                     SCOREBOARD S INNER JOIN PARTICIPANT P on S.PARTICIPANT_ID = P.PARTICIPANT_ID
                     INNER JOIN MEDAL M on S.MEDAL_ID = M.MEDAL_ID
                WHERE
                    S.OLYMPICS_ID = input_olympic_id
                GROUP BY
                    fname, lname, P.participant_id)
            WHERE ROWNUM <= k;
    END;

CREATE OR REPLACE PROCEDURE PROC_GET_CONNECTED(input_participant_id IN PARTICIPANT.participant_id%TYPE, n IN number, cursor_ OUT SYS_REFCURSOR) AS
    BEGIN
    IF (n = 1) THEN
        OPEN cursor_ FOR
        WITH
            olympic_participants as (SELECT fname || ' ' || lname as full_name, participant_id, olympics_id FROM PARTICIPANT NATURAL JOIN SCOREBOARD S), --selects all participants
            athletes as (SELECT DISTINCT full_name, participant_id, olympics_id FROM olympic_participants WHERE participant_id NOT IN (SELECT coach_id FROM TEAM where COACH_ID IS NOT NULL)), --filters out coaches
            participant_olympics AS (SELECT * FROM SCOREBOARD WHERE participant_id = input_participant_id), --selects information where given participant competed
            participant_name as (SELECT fname || ' ' || lname as full_name FROM participant_olympics INNER JOIN PARTICIPANT P ON P.PARTICIPANT_ID = participant_olympics.PARTICIPANT_ID ), --selects name of participant

            n_1_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id from athletes, participant_olympics
                                        WHERE athletes.olympics_id=participant_olympics.olympics_id AND athletes.participant_id!=participant_olympics.participant_id), --selects athletes in same olympic as participant
            n_1 as (SELECT DISTINCT athletes.olympics_id FROM athletes, n_1_compete WHERE athletes.participant_id = n_1_compete.participant_id
                                                                                                  AND athletes.olympics_id != n_1_compete.olympics_id AND
                                                                                                      athletes.olympics_id NOT IN (SELECT participant_olympics.olympics_id FROM participant_olympics))

        SELECT DISTINCT athletes.full_name, participant_name.full_name as name FROM ATHLETES, n_1, participant_name
        WHERE athletes.olympics_id = n_1.olympics_id AND athletes.participant_id NOT IN (SELECT n_1_compete.participant_id FROM n_1_compete);
    ELSIF (n = 2) THEN
        OPEN cursor_ FOR
        WITH
            olympic_participants as (SELECT fname || ' ' || lname as full_name, participant_id, olympics_id FROM PARTICIPANT NATURAL JOIN SCOREBOARD S), --selects all participants
            athletes as (SELECT DISTINCT full_name, participant_id, olympics_id FROM olympic_participants WHERE participant_id NOT IN (SELECT coach_id FROM TEAM where COACH_ID IS NOT NULL)), --filters out coaches
            participant_olympics AS (SELECT * FROM SCOREBOARD WHERE participant_id = input_participant_id), --selects information where given participant competed
            participant_name as (SELECT fname || ' ' || lname as full_name FROM participant_olympics INNER JOIN PARTICIPANT P ON P.PARTICIPANT_ID = participant_olympics.PARTICIPANT_ID ), --selects name of participant

            n_1_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id from athletes, participant_olympics
                                        WHERE athletes.olympics_id=participant_olympics.olympics_id AND athletes.participant_id!=participant_olympics.participant_id), --selects athletes in same olympic as participant

            n_2_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id FROM ATHLETES, n_1_compete WHERE athletes.olympics_id=n_1_compete.olympics_id AND athletes.participant_id != n_1_compete.olympics_id),

            n_2 as (SELECT DISTINCT athletes.olympics_id FROM athletes, n_2_compete WHERE athletes.participant_id = n_2_compete.participant_id AND athletes.olympics_id != n_2_compete.olympics_id AND athletes.OLYMPICS_ID
                not in (select n_1_compete.olympics_id FROM n_1_compete))

        SELECT DISTINCT athletes.full_name, participant_name.full_name as name FROM ATHLETES, n_2, participant_name
        WHERE athletes.olympics_id = n_2.olympics_id AND athletes.participant_id NOT IN (SELECT n_2_compete.participant_id FROM n_2_compete);
    ELSIF (n = 3) THEN
        OPEN cursor_ FOR
        WITH
            olympic_participants as (SELECT fname || ' ' || lname as full_name, participant_id, olympics_id FROM PARTICIPANT NATURAL JOIN SCOREBOARD S), --selects all participants
            athletes as (SELECT DISTINCT full_name, participant_id, olympics_id FROM olympic_participants WHERE participant_id NOT IN (SELECT coach_id FROM TEAM where COACH_ID IS NOT NULL)), --filters out coaches
            participant_olympics AS (SELECT * FROM SCOREBOARD WHERE participant_id = input_participant_id), --selects information where given participant competed
            participant_name as (SELECT fname || ' ' || lname as full_name FROM participant_olympics INNER JOIN PARTICIPANT P ON P.PARTICIPANT_ID = participant_olympics.PARTICIPANT_ID ), --selects name of participant

            n_1_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id from athletes, participant_olympics
                                        WHERE athletes.olympics_id=participant_olympics.olympics_id AND athletes.participant_id!=participant_olympics.participant_id), --selects athletes in same olympic as participant

            n_2_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id FROM ATHLETES, n_1_compete WHERE athletes.olympics_id=n_1_compete.olympics_id AND athletes.participant_id != n_1_compete.olympics_id),

            n_3_compete as (SELECT athletes.participant_id, athletes.full_name, athletes.olympics_id FROM ATHLETES, n_2_compete WHERE athletes.olympics_id=n_2_compete.olympics_id AND athletes.participant_id != n_2_compete.olympics_id),

            n_3 as (SELECT DISTINCT athletes.olympics_id FROM athletes, n_3_compete WHERE athletes.participant_id = n_3_compete.participant_id AND athletes.olympics_id != n_3_compete.olympics_id AND athletes.OLYMPICS_ID
                not in (select n_2_compete.olympics_id FROM n_2_compete))

        SELECT DISTINCT athletes.full_name, participant_name.full_name as name FROM ATHLETES, n_3, participant_name
        WHERE athletes.olympics_id = n_3.olympics_id AND athletes.participant_id NOT IN (SELECT n_3_compete.participant_id FROM n_3_compete);
    ELSE
        RAISE_APPLICATION_ERROR(-20000, 'Invalid value for n');
    END IF;
END;

CREATE OR REPLACE TRIGGER user_account_seqID
    BEFORE INSERT ON USER_ACCOUNT
    FOR EACH ROW
    BEGIN
        SELECT USER_SEQ.nextval
        INTO :NEW.user_id
        FROM DUAL;
    END;

CREATE OR REPLACE TRIGGER event_seqID
    BEFORE INSERT ON EVENT
    FOR EACH ROW
    BEGIN
        SELECT EVENT_SEQ.nextval
        INTO :NEW.event_id
        FROM DUAL;
    END;

CREATE OR REPLACE TRIGGER ENFORCE_CAPACITY
    BEFORE INSERT ON EVENT
    REFERENCING NEW AS new
    FOR EACH ROW
    DECLARE
        max_capacity VENUE.capacity%TYPE;
        current_count VENUE.capacity%type;
    BEGIN
        SELECT capacity
        INTO max_capacity
        FROM VENUE V
        WHERE :new.venue_id = V.venue_id;

        SELECT count(*) AS curr_event
        INTO current_count
        FROM EVENT E
        WHERE E.event_time = :new.event_time AND E.venue_id = :new.venue_id;

        IF ((current_count + 1) > max_capacity) THEN
            raise_application_error(-20000, 'Current venue is full at this time');
        END IF;
    END;

PURGE RECYCLEBIN;
COMMIT;
