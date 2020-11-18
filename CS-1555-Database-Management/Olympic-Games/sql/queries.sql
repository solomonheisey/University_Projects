-------------------------------------------------------------------------------------
-- Queries Used in Java Controller of Olympic Games Project Author: Solomon Heisey --
-------------------------------------------------------------------------------------

-- 1. createUser
INSERT INTO USER_ACCOUNT (username, passkey, role_id, last_login)
VALUES('testUsername', 'testKey', 1, '18-Apr-2020');

-- 2. dropUser
DELETE FROM USER_ACCOUNT WHERE username ='testUsername';

-- 3. createEvent
INSERT INTO EVENT(SPORT_ID, VENUE_ID, GENDER, EVENT_TIME)
VALUES(4, 2002, 1, '14-Aug-2004');

-- 4. addEventOutcome
INSERT INTO SCOREBOARD(olympics_id, event_id, team_id, participant_id, position)
VALUES(4, 1007, 120, 1, 3);

-- 5. createTeam
INSERT INTO TEAM(OLYMPICS_ID, TEAM_NAME, COUNTRY_ID, SPORT_ID, COACH_ID)
VALUES(4,'testTeam','United States', 1,149);

-- 6. registerTeam
INSERT INTO EVENT_PARTICIPATION(event_id, team_id, status)
VALUES(1007, 3, 'e');

-- 7. addParticipant
INSERT INTO PARTICIPANT(fname, lname, nationality, birth_place, dob)
VALUES('Solomon','Heisey','India','United States','05-Sep-1997');

-- 8. addTeamMember
INSERT INTO TEAM_MEMBER(TEAM_ID, PARTICIPANT_ID)
VALUES (11,4);

-- 9. dropTeamMember
DELETE FROM PARTICIPANT WHERE participant_ID = 1;

-- 10. login
SELECT * FROM USER_ACCOUNT WHERE username ='Guest' AND passkey ='GUEST';

-- 11. displaySport
DECLARE
  v_cur SYS_REFCURSOR;
  v_a   EVENT.event_id%TYPE;
  v_b   EVENT.gender%TYPE;
  v_c   varchar2(61);
  v_d   COUNTRY.country%TYPE;
BEGIN
  proc_get_events(1, v_cur);
  LOOP
    FETCH v_cur INTO v_a, v_b, v_c, v_d;
    EXIT WHEN v_cur%NOTFOUND;
    dbms_output.put_line(v_a || CHR(9) || v_b || CHR(9) || v_c || CHR(9) || v_d);
  END LOOP;
  CLOSE v_cur;
END;

-- 12. displayEvent
DECLARE
  v_cur SYS_REFCURSOR;
  v_a   varchar2(35);
  v_b   varchar2(35);
  v_c   varchar2(61);
  v_d   SCOREBOARD.position%TYPE;
  v_e   MEDAL.medal_title%TYPE;
BEGIN
  proc_get_participants(4, 1007, v_cur);
  LOOP
    FETCH v_cur INTO v_a, v_b, v_c, v_d, v_e;
    EXIT WHEN v_cur%NOTFOUND;
    dbms_output.put_line(v_a || CHR(9) || v_b || CHR(9) || v_c || CHR(9) || v_d || CHR(9) || v_e);
  END LOOP;
  CLOSE v_cur;
END;

-- 13. countryRanking
DECLARE
  v_cur SYS_REFCURSOR;
  v_a   integer;
  v_b   COUNTRY.country_code%TYPE;
  v_c   integer;
  v_d   integer;
  v_e   integer;
  v_f   integer;
  v_g   integer;
BEGIN
  proc_get_olympic(4, v_cur);
  LOOP
    FETCH v_cur INTO v_a, v_b, v_c, v_d, v_e, v_f, v_g;
    EXIT WHEN v_cur%NOTFOUND;
    dbms_output.put_line(v_a || CHR(9) || v_b || CHR(9) || v_c || CHR(9) || v_d || CHR(9) || v_e || CHR(9) || v_f  || CHR(9) || v_g);
  END LOOP;
  CLOSE v_cur;
END;

-- 14. topkAthletes
DECLARE
  v_cur SYS_REFCURSOR;
  v_a   integer;
  v_b   varchar2(61);
  v_c   PARTICIPANT.participant_id%TYPE;
  v_d   integer;
  v_e   integer;
  v_f   integer;
  v_g   integer;
BEGIN
  proc_get_athletes(1, 5,  v_cur);
  LOOP
    FETCH v_cur INTO v_a, v_b, v_c, v_d, v_e, v_f, v_g;
    EXIT WHEN v_cur%NOTFOUND;
    dbms_output.put_line(v_a || CHR(9) || v_b || CHR(9) || v_c || CHR(9) || v_d || CHR(9) || v_e || CHR(9) || v_f || CHR(9) || v_g);
  END LOOP;
  CLOSE v_cur;
END;

-- 15. connectedAthletes
DECLARE
  v_cur SYS_REFCURSOR;
  v_a   varchar2(61);
  v_b   varchar2(61);
BEGIN
  proc_get_connected(1, 1,  v_cur);
  LOOP
    FETCH v_cur INTO v_a, v_b;
    EXIT WHEN v_cur%NOTFOUND;
    dbms_output.put_line(v_a || CHR(9) || v_b);
  END LOOP;
  CLOSE v_cur;
END;

-- 16. logout
UPDATE USER_ACCOUNT SET last_login ='19-Apr-2020' WHERE user_id=7;

-- 17. exit
-- no database calls are used in method since it just closes the connection and exits