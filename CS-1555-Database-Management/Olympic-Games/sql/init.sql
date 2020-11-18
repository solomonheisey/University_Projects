--------------------------------------------------------------------------
--- Initialize Tables of Olympic Games Project Author: Solomon Heisey ---
--------------------------------------------------------------------------

INSERT INTO USER_ROLE
VALUES  (1,'Organizer');
INSERT INTO USER_ROLE
VALUES  (2,'Coach');
INSERT INTO USER_ROLE
VALUES  (3,'Guest');

INSERT INTO USER_ACCOUNT
VALUES (1, 'Carlos Arthur Nuzman', 'Rio', '1', '01-Sep-2019');
INSERT INTO USER_ACCOUNT
VALUES (2, 'Gianna Angelopoulos', 'Athens', '1', '11-Aug-2018');
INSERT INTO USER_ACCOUNT
VALUES (3, 'Hu Jintao', 'Beijing', '1', '05-Mar-2015');
INSERT INTO USER_ACCOUNT
VALUES (4, 'Sebastian Coe', 'London', '1', '23-Jul-2016');
INSERT INTO USER_ACCOUNT
VALUES(5,'Guest', 'GUEST', '3', '21-Apr-2014');
INSERT INTO USER_ACCOUNT
VALUES(6,'Aimee Boorman', 'AB', '2', '21-Nov-2012');
INSERT INTO USER_ACCOUNT
VALUES(7,'Alexander Alexandrov', 'AA', '2', '15-Mar-2013');
INSERT INTO USER_ACCOUNT
VALUES(8,'Liang Chow', 'LC', '2', '13-Apr-2020');
INSERT INTO USER_ACCOUNT
VALUES(9,'John Geddert', 'JG', '2', '19-Oct-2020');
INSERT INTO USER_ACCOUNT
VALUES(10,'Octavian Bellu', 'OB', '2', '13-Apr-2020');
INSERT INTO USER_ACCOUNT
VALUES(11,'Marta	Karolyi', 'MK', '2', '13-Apr-2020');
INSERT INTO USER_ACCOUNT
VALUES(12,'Leonid Arkayev', 'LA', '2', '04-Sep-2016');

INSERT INTO OLYMPICS
VALUES (1, 'XXXI', 'Rio', '05-Aug-2016', '21-Aug-2016', 'https://www.olympic.org/rio-2016');
INSERT INTO OLYMPICS
VALUES (2, 'XXX', 'London', '27-July-2012', '12-Aug-2012', 'https://www.olympic.org/london-2012');
INSERT INTO OLYMPICS
VALUES (3, 'XXIX', 'Beijing', '08-Aug-2008', '24-Aug-2008', 'https://www.olympic.org/beijing-2008');
INSERT INTO OLYMPICS
VALUES (4, 'XXVIII', 'Athens', '13-Aug-2004', '29-Aug-2004', 'https://www.olympic.org/athens-2004');

INSERT INTO SPORT
VALUES(1, 'Gymnastics', null, '06-Apr-1896', 5);
INSERT INTO SPORT
VALUES(2, 'Rowing', null, '25-Aug-1900', 2);
INSERT INTO SPORT
VALUES(3, 'Tennis', null, '09-Apr-1896', 2);
INSERT INTO SPORT
VALUES(4, 'Boxing', null, '01-July-1904', 1);
INSERT INTO SPORT
VALUES(5, 'Weightlifting', null, '14-Aug-1920', 1);
INSERT INTO SPORT
VALUES(6, 'Archery', null, '01-May-1900', 1);

INSERT INTO MEDAL
VALUES(1, 'gold', 6);
INSERT INTO MEDAL
VALUES(2, 'silver', 2);
INSERT INTO MEDAL
VALUES(3, 'bronze', 1);
INSERT INTO MEDAL
VALUES(4, 'none', 0);

INSERT INTO COUNTRY
VALUES(111, 'India', 'IND');
INSERT INTO COUNTRY
VALUES(106, 'New Zealand', 'NZL');
INSERT INTO COUNTRY
VALUES(1006, 'Turkey', 'TUR');
INSERT INTO COUNTRY
VALUES(116, 'Argentina', 'ARG');
INSERT INTO COUNTRY
VALUES(1005, 'France', 'FRA');
INSERT INTO COUNTRY
VALUES(1004, 'Australia', 'AUS');
INSERT INTO COUNTRY
VALUES(1003, 'Mexico', 'MEX');
INSERT INTO COUNTRY
VALUES(1002, 'Indonesia', 'INA');
INSERT INTO COUNTRY
VALUES(1001, 'Ireland', 'IRL');
INSERT INTO COUNTRY
VALUES(14, 'Spain', 'ESP');
INSERT INTO COUNTRY
VALUES(15, 'Romania', 'ROU');
INSERT INTO COUNTRY
VALUES(11, 'Poland', 'POL');
INSERT INTO COUNTRY
VALUES(12, 'United Kingdom', 'GBR');
INSERT INTO COUNTRY
VALUES(13, 'Lithuania', 'LTU');
INSERT INTO COUNTRY
VALUES(10, 'Russia', 'RUS');
INSERT INTO COUNTRY
VALUES(8, 'South Korea', 'KOR');
INSERT INTO COUNTRY
VALUES(9, 'Germany', 'DEU');
INSERT INTO COUNTRY
VALUES(5, 'China', 'CHN');
INSERT INTO COUNTRY
VALUES(6, 'North Korea', 'PRK');
INSERT INTO COUNTRY
VALUES(7, 'Thailand', 'THA');
INSERT INTO COUNTRY
VALUES(1, 'Uzbekistan', 'UZB');
INSERT INTO COUNTRY
VALUES(2, 'Colombia', 'COL');
INSERT INTO COUNTRY
VALUES(3, 'Cuba', 'CUB');
INSERT INTO COUNTRY
VALUES(4, 'United States', 'USA');

-----------------------------------------------------------------------------------------------

--Men's light flyweight boxing 2016
INSERT INTO PARTICIPANT
VALUES(1, 'Hasanboy', 'Dusmatov', 'Uzbekistan', 'Uzbekistan', '24-Jun-1993');
INSERT INTO PARTICIPANT
VALUES(2, 'Yuberjen', 'Martinez', 'Colombia', 'Colombia', '13-Apr-1984');
INSERT INTO PARTICIPANT
VALUES(3, 'Joahnys', 'Argilagos', 'Cuba', 'Cuba', '04-Nov-1979');
INSERT INTO PARTICIPANT
VALUES(4, 'Nico', 'Hernandez', 'United States', 'United States', '04-Jan-1996');

INSERT INTO TEAM
VALUES(1, 1, 'Uzbekistan', 1, 4, null);
INSERT INTO TEAM
VALUES(2, 1, 'Colombia', 2, 4, null);
INSERT INTO TEAM
VALUES(3, 1, 'Cuba', 3, 4, null);
INSERT INTO TEAM
VALUES(4, 1, 'USA', 4, 4, null);

INSERT INTO TEAM_MEMBER
VALUES(1,1);
INSERT INTO TEAM_MEMBER
VALUES(2, 2);
INSERT INTO TEAM_MEMBER
VALUES(3,3);
INSERT INTO TEAM_MEMBER
VALUES(4,4);

INSERT INTO VENUE
VALUES(1, 1, 'Riocentro - Pavilion 6', 1);

INSERT INTO EVENT
VALUES(1, 4, 1,1, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,1,1,1,1,1);
INSERT INTO SCOREBOARD
VALUES(1,1,2,2,2,2);
INSERT INTO SCOREBOARD
VALUES(1,1,3,3,3,3);
INSERT INTO SCOREBOARD
VALUES(1,1,4,4,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1,1,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1,2,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1,3,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1,4,'e');

----------------------------------------------------------------------------------------------------------

--Men's 56 kg Weightlifting 2016
INSERT INTO PARTICIPANT
VALUES(5, 'Long', 'Qingquan', 'China', 'China', '03-Dec-1990');
INSERT INTO PARTICIPANT
VALUES(6, 'Om', 'Yun-chol', 'North Korea', 'North Korea', '18-Nov-1991');
INSERT INTO PARTICIPANT
VALUES(7, 'Sinphet', 'Kruaithong', 'Thailand', 'Thailand', '04-Oct-1991');

INSERT INTO TEAM
VALUES(5, 1, 'China', 5, 5, null);
INSERT INTO TEAM
VALUES(6, 1, 'North Korea', 6, 5, null);
INSERT INTO TEAM
VALUES(7, 1, 'Thailand', 7, 5, null);

INSERT INTO TEAM_MEMBER
VALUES(5,5);
INSERT INTO TEAM_MEMBER
VALUES(6, 6);
INSERT INTO TEAM_MEMBER
VALUES(7,7);

INSERT INTO VENUE
VALUES(2, 1, 'Riocentro - Pavilion 2', 1);

INSERT INTO EVENT
VALUES(2, 5, 2,1, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,2,5,5,1,1);
INSERT INTO SCOREBOARD
VALUES(1,2,6,6,2,2);
INSERT INTO SCOREBOARD
VALUES(1,2,7,7,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(2,5,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2,6,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2,7,'e');

----------------------------------------------------------------------------------------------------------

--Women's Individual Archery 2016
INSERT INTO PARTICIPANT
VALUES(8, 'Chang', 'Hye-jin', 'South Korea', 'South Korea', '13-May-1987');
INSERT INTO PARTICIPANT
VALUES(9, 'Lisa', 'Unruh', 'Germany', 'Germany', '12-April-1988');
INSERT INTO PARTICIPANT
VALUES(10, 'Ki', 'Bo-bae', 'South Korea', 'South Korea', '20-Feb-1988');

INSERT INTO TEAM
VALUES(8, 1, 'South Korea', 8, 6, null);
INSERT INTO TEAM
VALUES(9, 1, 'Germany', 9, 6, null);
INSERT INTO TEAM
VALUES(10, 1, 'South Korea', 9, 6, null);

INSERT INTO TEAM_MEMBER
VALUES(8,8);
INSERT INTO TEAM_MEMBER
VALUES(9, 9);
INSERT INTO TEAM_MEMBER
VALUES(10,10);

INSERT INTO VENUE
VALUES(3, 1, 'Sambadrome Marques de Sapucai', 2);

INSERT INTO EVENT
VALUES(3, 6, 3,0, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,3,8,8,1,1);
INSERT INTO SCOREBOARD
VALUES(1,3,9,9,2,2);
INSERT INTO SCOREBOARD
VALUES(1,3,10,10,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(3,8,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(3,9,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(3,10,'e');

-------------------------------------------------------------------------------------------------------------


--Women's Team all-around gymnastics 2016
INSERT INTO PARTICIPANT
VALUES(11, 'Simone', 'Biles', 'United States', 'United States', '14-Mar-1997');
INSERT INTO PARTICIPANT
VALUES(12, 'Gabby', 'Douglas', 'United States', 'United States', '31-Dec-1995');
INSERT INTO PARTICIPANT
VALUES(13, 'Laurie', 'Hernandez', 'United States', 'United States', '13-Apr-1987');
INSERT INTO PARTICIPANT
VALUES(14, 'Madison', 'Kocian', 'United States', 'United States', '17-Apr-1987');
INSERT INTO PARTICIPANT
VALUES(15, 'Aly', 'Raisman', 'United States', 'United States', '04-Oct-1984');
INSERT INTO PARTICIPANT
VALUES(26, 'Aimee', 'Boorman', 'United States', 'United States', '30-Apr-1987');
INSERT INTO PARTICIPANT
VALUES(16, 'Angelina', 'Melnikova', 'Russia', 'Russia', '18-Jul-2000');
INSERT INTO PARTICIPANT
VALUES(17, 'Aliya', 'Mustafina', 'Russia', 'Russia', '30-Sep-1994');
INSERT INTO PARTICIPANT
VALUES(18, 'Maria', 'Paseka', 'Russia', 'Russia', '19-Jul-1995');
INSERT INTO PARTICIPANT
VALUES(19, 'Daria', 'Spiridonova', 'Russia', 'Russia', '18-Jul-1998');
INSERT INTO PARTICIPANT
VALUES(20, 'Seda', 'Tutkhalyan', 'Russia', 'Russia', '07-Dec-1984');
INSERT INTO PARTICIPANT
VALUES(27, 'Alexander', 'Alexandrov', 'Russia', 'Russia', '02-Dec-1980');
INSERT INTO PARTICIPANT
VALUES(21, 'Fan', 'Yilin', 'China', 'China', '11-Nov-1999');
INSERT INTO PARTICIPANT
VALUES(22, 'Mao', 'Yi', 'China', 'China', '16-Sep-1999');
INSERT INTO PARTICIPANT
VALUES(23, 'Shang', 'Chungsong', 'China', 'China', '18-Mar-1996');
INSERT INTO PARTICIPANT
VALUES(24, 'Tan', 'Jiaxin', 'China', 'China', '03-Dec-1996');
INSERT INTO PARTICIPANT
VALUES(25, 'Wang', 'Yan', 'China', 'China', '30-Oct-1999');
INSERT INTO PARTICIPANT
VALUES(28, 'Liang', 'Chow', 'China', 'China', '03-Oct-1987');

INSERT INTO TEAM
VALUES(11, 1, 'United States', 4, 1, 26);
INSERT INTO TEAM
VALUES(12, 1, 'Russia', 10, 1, 27);
INSERT INTO TEAM
VALUES(13, 1, 'China', 5, 1, 28);

INSERT INTO TEAM_MEMBER
VALUES(11,11);
INSERT INTO TEAM_MEMBER
VALUES(11,12);
INSERT INTO TEAM_MEMBER
VALUES(11,13);
INSERT INTO TEAM_MEMBER
VALUES(11,14);
INSERT INTO TEAM_MEMBER
VALUES(11,15);
INSERT INTO TEAM_MEMBER
VALUES(12,16);
INSERT INTO TEAM_MEMBER
VALUES(12,17);
INSERT INTO TEAM_MEMBER
VALUES(12,18);
INSERT INTO TEAM_MEMBER
VALUES(12,19);
INSERT INTO TEAM_MEMBER
VALUES(12,20);
INSERT INTO TEAM_MEMBER
VALUES(13,21);
INSERT INTO TEAM_MEMBER
VALUES(13,22);
INSERT INTO TEAM_MEMBER
VALUES(13,23);
INSERT INTO TEAM_MEMBER
VALUES(13,24);
INSERT INTO TEAM_MEMBER
VALUES(13,25);

INSERT INTO VENUE
VALUES(4, 1, 'Arena Olimpica do Rio', 1);

INSERT INTO EVENT
VALUES(4, 1, 4,0, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,4,11,11,1,1);
INSERT INTO SCOREBOARD
VALUES(1,4,11,12,1,1);
INSERT INTO SCOREBOARD
VALUES(1,4,11,13,1,1);
INSERT INTO SCOREBOARD
VALUES(1,4,11,14,1,1);
INSERT INTO SCOREBOARD
VALUES(1,4,11,15,1,1);
INSERT INTO SCOREBOARD
VALUES(1,4,12,16,2,2);
INSERT INTO SCOREBOARD
VALUES(1,4,12,17,2,2);
INSERT INTO SCOREBOARD
VALUES(1,4,12,18,2,2);
INSERT INTO SCOREBOARD
VALUES(1,4,12,19,2,2);
INSERT INTO SCOREBOARD
VALUES(1,4,12,20,2,2);
INSERT INTO SCOREBOARD
VALUES(1,4,13,21,3,3);
INSERT INTO SCOREBOARD
VALUES(1,4,13,22,3,3);
INSERT INTO SCOREBOARD
VALUES(1,4,13,23,3,3);
INSERT INTO SCOREBOARD
VALUES(1,4,13,24,3,3);
INSERT INTO SCOREBOARD
VALUES(1,4,13,25,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(4,11,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(4,12,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(4,13,'e');

----------------------------------------------------------------------------------------------------------------


-- Women's Rowing (Double sculls) 2016
INSERT INTO PARTICIPANT
VALUES(29, 'Magdalena', 'Fularczyk', 'Poland', 'Poland', '06-Nov-1978');
INSERT INTO PARTICIPANT
VALUES(30, 'Natalia', 'Madaj', 'Poland', 'Poland', '25-Jan-1988');
INSERT INTO PARTICIPANT
VALUES(31, 'Victoria', 'Thornley', 'United Kingdom', 'Great Britain', '30-Nov-1987');
INSERT INTO PARTICIPANT
VALUES(32, 'Katherine', 'Grainger', 'United Kingdom', 'Great Britain', '07-Oct-1989');
INSERT INTO PARTICIPANT
VALUES(33, 'Donata', 'Vistartaite', 'Lithuania', 'Lithuania', '11-Jun-1989');
INSERT INTO PARTICIPANT
VALUES(34, 'Milda', 'Valciukaite', 'Lithuania' , 'Lithuania', '24-May-1994');

INSERT INTO TEAM
VALUES(14, 1, 'Poland', 11, 2, null);
INSERT INTO TEAM
VALUES(15, 1, 'United Kingdom', 12, 2, null);
INSERT INTO TEAM
VALUES(16, 1, 'Lithuania', 13, 2, null);

INSERT INTO TEAM_MEMBER
VALUES(14,29);
INSERT INTO TEAM_MEMBER
VALUES(14,30);
INSERT INTO TEAM_MEMBER
VALUES(15, 31);
INSERT INTO TEAM_MEMBER
VALUES(15,32);
INSERT INTO TEAM_MEMBER
VALUES(16,33);
INSERT INTO TEAM_MEMBER
VALUES(16,34);

INSERT INTO VENUE
VALUES(5, 1, 'Rodrigo de Freitas Lagoon', 2);

INSERT INTO EVENT
VALUES(5, 2, 5,0, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,5,14,29,1,1);
INSERT INTO SCOREBOARD
VALUES(1,5,14,30,1,1);
INSERT INTO SCOREBOARD
VALUES(1,5,15,31,2,2);
INSERT INTO SCOREBOARD
VALUES(1,5,15,32,2,2);
INSERT INTO SCOREBOARD
VALUES(1,5,16,33,3,3);
INSERT INTO SCOREBOARD
VALUES(1,5,16,34,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(5,14,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(5,15,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(5,16,'e');

--------------------------------------------------------------------------------------------------------------


-- Men's Tennis (doubles) 2016
INSERT INTO PARTICIPANT
VALUES(35, 'Marc', 'Lopez', 'Spain', 'Spain', '31-Jul-1982');
INSERT INTO PARTICIPANT
VALUES(36, 'Rafael', 'Nadal', 'Spain', 'Spain', '03-Jun-1986');

INSERT INTO PARTICIPANT
VALUES(37, 'Florin', 'Mergea', 'Romania', 'Romania', '26-Jan-1985');
INSERT INTO PARTICIPANT
VALUES(38, 'Horia', 'Tecau', 'Romania', 'Romania', '19-Jan-1985');

INSERT INTO PARTICIPANT
VALUES(39, 'Steve', 'Johnson', 'United States', 'United States', '24-Dec-1989');
INSERT INTO PARTICIPANT
VALUES(40, 'Jack', 'Sock', 'United States' , 'United States', '24-Sep-1992');

INSERT INTO TEAM
VALUES(17, 1, 'Spain', 14, 3, null);
INSERT INTO TEAM
VALUES(18, 1, 'Romania', 15, 3, null);
INSERT INTO TEAM
VALUES(19, 1, 'United States', 4, 3, null);

INSERT INTO TEAM_MEMBER
VALUES(17,35);
INSERT INTO TEAM_MEMBER
VALUES(17,36);
INSERT INTO TEAM_MEMBER
VALUES(18, 37);
INSERT INTO TEAM_MEMBER
VALUES(18,38);
INSERT INTO TEAM_MEMBER
VALUES(19,39);
INSERT INTO TEAM_MEMBER
VALUES(19,40);

INSERT INTO VENUE
VALUES(6, 1, 'Olympic Tennis Centre', 1);

INSERT INTO EVENT
VALUES(6, 3, 6,1, '06-Aug-2016');

INSERT INTO SCOREBOARD
VALUES(1,6,17,35,1,1);
INSERT INTO SCOREBOARD
VALUES(1,6,17,36,1,1);
INSERT INTO SCOREBOARD
VALUES(1,6,18,37,2,2);
INSERT INTO SCOREBOARD
VALUES(1,6,18,38,2,2);
INSERT INTO SCOREBOARD
VALUES(1,6,19,39,3,3);
INSERT INTO SCOREBOARD
VALUES(1,6,19,40,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(6,17,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(6,18,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(6,19,'e');


----------------------------------------------------------------
----------------------End of 2016 data--------------------------
----------------------------------------------------------------
---------------------Start of 2012 data-------------------------
----------------------------------------------------------------

-----------------------------------------------------------------------------------------------

--Men's Light flyweight boxing 2012
INSERT INTO PARTICIPANT
VALUES(1001, 'Shiming', 'Zou', 'China', 'China', '18-May-1981');
INSERT INTO PARTICIPANT
VALUES(1002, 'Kaeo', 'Pongprayoon', 'Thailand', 'Thailand', '28-Mar-1980');
INSERT INTO PARTICIPANT
VALUES(1003, 'Paddy', 'Barnes', 'Ireland', 'United Kingdom', '09-Apr-1987');
INSERT INTO PARTICIPANT
VALUES(1004, 'David', 'Ayrapetyan', 'Russia', 'USSR', '26-Sep-1983');

INSERT INTO TEAM
VALUES(1001, 2, 'China', 5, 4, null);
INSERT INTO TEAM
VALUES(1002, 2, 'Thailand', 7, 4, null);
INSERT INTO TEAM
VALUES(1003, 2, 'Ireland', 01, 4, null);
INSERT INTO TEAM
VALUES(1004, 2, 'Russia', 10, 4, null);

INSERT INTO TEAM_MEMBER
VALUES(1001, 1001);
INSERT INTO TEAM_MEMBER
VALUES(1002, 1002);
INSERT INTO TEAM_MEMBER
VALUES(1003, 1003);
INSERT INTO TEAM_MEMBER
VALUES(1004, 1004);

INSERT INTO VENUE
VALUES(1001, 2, 'ExCeL Exhibition Centre', 5); --5 can hold 5 events at a time

INSERT INTO EVENT
VALUES(1001, 4, 1001, 1, '31-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1001,1001,1001,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1001,1002,1002,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1001,1003,1003,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1001,1004,1004,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1001,1001,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1001,1002,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1001,1003,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1001,04,'e');

----------------------------------------------------------------
----------------------------------------------------------------

--Men's 56 kg Weightlifting 2012
INSERT INTO PARTICIPANT
VALUES(1005, 'Wu', 'Jingbiao', 'China', 'China', '10-Jan-1989');
INSERT INTO PARTICIPANT
VALUES(1006, 'Jadi', 'Setiadi', 'Indonesia', 'Indonesia', '02-Feb-1985');

INSERT INTO TEAM
VALUES(1005, 2, 'North Korea', 5, 5, null);
INSERT INTO TEAM
VALUES(1006, 2, 'China', 6, 5, null);
INSERT INTO TEAM
VALUES(1007, 2, 'Indonesia', 7, 5, null);

INSERT INTO TEAM_MEMBER
VALUES(1005, 6);
INSERT INTO TEAM_MEMBER
VALUES(1006, 1005);
INSERT INTO TEAM_MEMBER
VALUES(1007,1006);

INSERT INTO EVENT
VALUES(1002, 5, 1001, 1, '29-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1002,1005,6,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1002,1006,1005,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1002,1007,1006,5,4);--he did not win a medal

INSERT INTO EVENT_PARTICIPATION
VALUES(1002,1005,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1002,1006,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1002,1007,'e');

----------------------------------------------------------------
----------------------------------------------------------------

--Women's Individual Archery 2016
INSERT INTO PARTICIPANT
VALUES(1007, 'Aida', 'Roman', 'Mexico', 'Mexico', '21-May-1988');
INSERT INTO PARTICIPANT
VALUES(1008, 'Mariana', 'Avitia', 'Mexico', 'Mexico', '18-Sep-1993');

INSERT INTO TEAM
VALUES(1008, 2, 'South Korea', 8, 6, null);
INSERT INTO TEAM
VALUES(1009, 2, 'Mexico', 03, 6, null);
INSERT INTO TEAM
VALUES(1010, 2, 'Mexico', 03, 6, null);

INSERT INTO TEAM_MEMBER
VALUES(1008,10);
INSERT INTO TEAM_MEMBER
VALUES(1009,1007);
INSERT INTO TEAM_MEMBER
VALUES(1010,1008);

INSERT INTO VENUE
VALUES(1002, 2, 'Lord''s Cricket Ground', 1); --only 1 event at a time

INSERT INTO EVENT
VALUES(1003, 6, 1002, 0, '27-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1003,1008,10,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1003,1009,1007,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1003,1010,1008,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1003,1008,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1003,1009,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1003,1010,'e');

----------------------------------------------------------------
----------------------------------------------------------------

--Women's Team all-around gymnastics 2012
INSERT INTO PARTICIPANT
VALUES(1009, 'Jordyn', 'Wieber', 'United States', 'United States', '12-Jul-1995');
INSERT INTO PARTICIPANT
VALUES(1010, 'Kyla', 'Ross', 'United States', 'United States', '24-Oct-1996');
INSERT INTO PARTICIPANT
VALUES(1011, 'McKayla', 'Maroney', 'United States', 'United States', '09-Dec-1995');
INSERT INTO PARTICIPANT
VALUES(1012, 'John', 'Geddert', 'United States', 'United States', '21-Dec-1957'); --coach
INSERT INTO PARTICIPANT
VALUES(1013, 'Viktoria', 'Komova', 'Russia', 'Russia', '30-Jan-1995');
INSERT INTO PARTICIPANT
VALUES(1014, 'Ksenia', 'Afanasyeva', 'Russia', 'Russia', '13-Sep-1991');
INSERT INTO PARTICIPANT
VALUES(1015, 'Anastasia', 'Grishina', 'Russia', 'Russia', '16-Jan-1996');
INSERT INTO PARTICIPANT
VALUES(1016, 'Catalina', 'Ponor', 'Romania', 'Romania', '20-Aug-1987');
INSERT INTO PARTICIPANT
VALUES(1017, 'Larisa', 'Iordache', 'Romania', 'Romania', '19-Jun-1996');
INSERT INTO PARTICIPANT
VALUES(1018, 'Diana', 'Bulimar', 'Romania', 'Romania', '22-Aug-1995');
INSERT INTO PARTICIPANT
VALUES(1019, 'Sandra', 'Izbasa', 'Romania', 'Romania', '18-Jun-1990');
INSERT INTO PARTICIPANT
VALUES(1020, 'Diana', 'Chelaru', 'Romania', 'Romania', '15-Aug-1993');
INSERT INTO PARTICIPANT
VALUES(1021, 'Octavian', 'Bellu', 'Romania', 'Romania', '17-Feb-1951'); --coach


INSERT INTO TEAM
VALUES(1011, 2, 'United States', 4, 1, 1012);
INSERT INTO TEAM
VALUES(1012, 2, 'Russia', 10, 1, 27);
INSERT INTO TEAM
VALUES(1013, 2, 'Romania', 15, 1, 1021);

INSERT INTO TEAM_MEMBER
VALUES(1011,1009);
INSERT INTO TEAM_MEMBER
VALUES(1011,12);
INSERT INTO TEAM_MEMBER
VALUES(1011,15);
INSERT INTO TEAM_MEMBER
VALUES(1011,1010);
INSERT INTO TEAM_MEMBER
VALUES(1011,1011);
INSERT INTO TEAM_MEMBER
VALUES(1012,17);
INSERT INTO TEAM_MEMBER
VALUES(1012,1013);
INSERT INTO TEAM_MEMBER
VALUES(1012,1014);
INSERT INTO TEAM_MEMBER
VALUES(1012,1015);
INSERT INTO TEAM_MEMBER
VALUES(1012,18);
INSERT INTO TEAM_MEMBER
VALUES(1013,1016);
INSERT INTO TEAM_MEMBER
VALUES(1013,1017);
INSERT INTO TEAM_MEMBER
VALUES(1013,1018);
INSERT INTO TEAM_MEMBER
VALUES(1013,1019);
INSERT INTO TEAM_MEMBER
VALUES(1013,1020);

INSERT INTO VENUE
VALUES(1003, 2, 'North Greenwich Arena', 1); --1 event at a time

INSERT INTO EVENT
VALUES(1004, 1, 1003, 0, '31-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1004,1011,1009,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1004,1011,12,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1004,1011,15,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1004,1011,1010,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1004,1011,1011,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1004,1012,17,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1004,1012,18,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1004,1012,1013,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1004,1012,1014,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1004,1012,1015,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1004,1013,1016,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1004,1013,1017,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1004,1013,1018,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1004,1013,1019,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1004,1013,1020,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1004,1011,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1004,1012,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1004,1013,'e');

----------------------------------------------------------------
----------------------------------------------------------------

-- Women's Rowing (Double sculls) 2012
INSERT INTO PARTICIPANT
VALUES(1022, 'Anna', 'Watkins', 'United Kingdom', 'Great Britain', '12-Feb-1983');
INSERT INTO PARTICIPANT
VALUES(1023, 'Julia', 'Michalska', 'Poland', 'Poland', '21-Jul-1985');
INSERT INTO PARTICIPANT
VALUES(1024, 'Kimberley', 'Brennan', 'Australia', 'Australia', '09-Aug-1985');
INSERT INTO PARTICIPANT
VALUES(1025, 'Brooke', 'Pratley', 'Australia' , 'Australia', '06-Apr-1980');

INSERT INTO TEAM
VALUES(1014, 2, 'United Kingdom', 12, 2, null);
INSERT INTO TEAM
VALUES(1015, 2, 'Poland', 11, 2, null);
INSERT INTO TEAM
VALUES(1016, 2, 'Australia', 04, 2, null);

INSERT INTO TEAM_MEMBER
VALUES(1014,1022);
INSERT INTO TEAM_MEMBER
VALUES(1014,32);
INSERT INTO TEAM_MEMBER
VALUES(1015,1023);
INSERT INTO TEAM_MEMBER
VALUES(1015,29);
INSERT INTO TEAM_MEMBER
VALUES(1016,1024);
INSERT INTO TEAM_MEMBER
VALUES(1016,1025);

INSERT INTO VENUE
VALUES(1004, 2, 'Eton Dorney', 1); --1 event at a time

INSERT INTO EVENT
VALUES(1005, 2, 1004,0, '30-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1005,1014,1022,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1005,1014,32,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1005,1015,1023,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1005,1015,29,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1005,1016,1024,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1005,1016,1025,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1005,1014,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1005,1015,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1005,1016,'e');

----------------------------------------------------------------
----------------------------------------------------------------

-- Men's Tennis (doubles) 2012
INSERT INTO PARTICIPANT
VALUES(1026, 'Robert', 'Bryan', 'United States', 'United States', '29-Apr-1978');
INSERT INTO PARTICIPANT
VALUES(1027, 'Michael', 'Bryan', 'United States' , 'United States', '29-Apr-1978');

INSERT INTO PARTICIPANT
VALUES(1028, 'Jo-Wilfried', 'Tsonga', 'France', 'France', '17-Apr-1985');
INSERT INTO PARTICIPANT
VALUES(1029, 'Michael', 'Llodra', 'France', 'France', '18-May-1980');

INSERT INTO PARTICIPANT
VALUES(1030, 'Julien', 'Benneteau', 'France', 'France', '20-Dec-1981');
INSERT INTO PARTICIPANT
VALUES(1031, 'Richard', 'Gasquet', 'France', 'France', '18-Jun-1986');

INSERT INTO TEAM
VALUES(1017, 2, 'United States', 4, 3, null);
INSERT INTO TEAM
VALUES(1018, 2, 'France', 1005, 3, null);
INSERT INTO TEAM
VALUES(1019, 2, 'France', 1005, 3, null);

INSERT INTO TEAM_MEMBER
VALUES(1017,1026);
INSERT INTO TEAM_MEMBER
VALUES(1017,1027);
INSERT INTO TEAM_MEMBER
VALUES(1018,1028);
INSERT INTO TEAM_MEMBER
VALUES(1018,1029);
INSERT INTO TEAM_MEMBER
VALUES(1019,1030);
INSERT INTO TEAM_MEMBER
VALUES(1019,1031);

INSERT INTO VENUE
VALUES(1005, 2, 'England Lawn Tennis & Croquet', 18);

INSERT INTO EVENT
VALUES(1006, 3, 1005,1, '28-Jul-2012');

INSERT INTO SCOREBOARD
VALUES(2,1006,1017,1026,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1006,1017,1027,1,1);
INSERT INTO SCOREBOARD
VALUES(2,1006,1018,1028,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1006,1018,1029,2,2);
INSERT INTO SCOREBOARD
VALUES(2,1006,1019,1030,3,3);
INSERT INTO SCOREBOARD
VALUES(2,1006,1019,1031,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1006,1017,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1006,1018,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1006,1019,'e');

----------------------------------------------------------------
----------------------------------------------------------------
----------------------End of 2012 data--------------------------
----------------------------------------------------------------
----------------------------------------------------------------


----------------------------------------------------------------
----------------------------------------------------------------
----------------------Gymnastics 2004---------------------------
----------------------------------------------------------------
----------------------------------------------------------------


--Women's Team all-around gymnastics 2004
INSERT INTO PARTICIPANT
VALUES(1032, 'Oana', 'Ban', 'Romania', 'Romania', '11-Jan-1986');
INSERT INTO PARTICIPANT
VALUES(1033, 'Alexandra', 'Eremia', 'Romania', 'Romania', '19-Feb-1987');
INSERT INTO PARTICIPANT
VALUES(1034, 'Monica', 'Rosu', 'Romania', 'Romania', '11-May-1987');
INSERT INTO PARTICIPANT
VALUES(1035, 'Nicoleta', 'Sofronie', 'Romania', 'Romania', '12-Feb-1988');
INSERT INTO PARTICIPANT
VALUES(1036, 'Silvia', 'Stroescu', 'Romania', 'Romania', '08-May-1985');
INSERT INTO PARTICIPANT
VALUES(1037, 'Mohini', 'Bhardwaj', 'United States', 'United States', '29-Sep-1978');
INSERT INTO PARTICIPANT
VALUES(1038, 'Annia', 'Hatch', 'Cuba', 'Cuba', '12-Jun-1978');
INSERT INTO PARTICIPANT
VALUES(1039, 'Terin', 'Humphrey', 'United States', 'United States', '15-Aug-1986');
INSERT INTO PARTICIPANT
VALUES(1040, 'Courtney', 'Kupets', 'United States', 'United States', '27-Jul-1986');
INSERT INTO PARTICIPANT
VALUES(1041, 'Courtney', 'McCool', 'United States', 'United States', '01-Apr-1988');
INSERT INTO PARTICIPANT
VALUES(1042, 'Carly', 'Patterson', 'United States', 'United States', '04-Feb-1988');
INSERT INTO PARTICIPANT
VALUES(1043, 'Marta', 'Karolyi', 'Romania', 'Romania', '29-Aug-1942'); --coach of usa
INSERT INTO PARTICIPANT
VALUES(1044, 'Ludmila', 'Ezhova', 'Russia', 'Russia', '04-Mar-1982');
INSERT INTO PARTICIPANT
VALUES(1045, 'Svetlana', 'Khorkina', 'Russia', 'Russia', '19-Jan-1979');
INSERT INTO PARTICIPANT
VALUES(1046, 'Maria', 'Kryuchkova', 'Russia', 'Russia', '07-Jul-1988');
INSERT INTO PARTICIPANT
VALUES(1047, 'Anna', 'Pavlova', 'Russia', 'Russia', '06-Sep-1987');
INSERT INTO PARTICIPANT
VALUES(1048, 'Elena', 'Zamolodchikova', 'Russia', 'Russia', '19-Sep-1982');
INSERT INTO PARTICIPANT
VALUES(1049, 'Natalia', 'Ziganshina', 'Russia', 'Russia', '24-Dec-1985');
INSERT INTO PARTICIPANT
VALUES(1050, 'Leonid', 'Arkayev', 'Russia', 'Russia', '03-Jun-1940'); --coach

INSERT INTO TEAM
VALUES(1020, 4, 'Romania', 15, 1, 1021);
INSERT INTO TEAM
VALUES(1021, 4, 'United States', 4, 1, 1043);
INSERT INTO TEAM
VALUES(1022, 4, 'Russia', 10, 1, 1050);

INSERT INTO TEAM_MEMBER
VALUES(1020,1016);
INSERT INTO TEAM_MEMBER
VALUES(1020,1032);
INSERT INTO TEAM_MEMBER
VALUES(1020,1033);
INSERT INTO TEAM_MEMBER
VALUES(1020,1034);
INSERT INTO TEAM_MEMBER
VALUES(1020,1035);
INSERT INTO TEAM_MEMBER
VALUES(1020,1036);
INSERT INTO TEAM_MEMBER
VALUES(1021,1037);
INSERT INTO TEAM_MEMBER
VALUES(1021,1038);
INSERT INTO TEAM_MEMBER
VALUES(1021,1039);
INSERT INTO TEAM_MEMBER
VALUES(1021,1040);
INSERT INTO TEAM_MEMBER
VALUES(1021,1041);
INSERT INTO TEAM_MEMBER
VALUES(1021,1042);
INSERT INTO TEAM_MEMBER
VALUES(1022,1044);
INSERT INTO TEAM_MEMBER
VALUES(1022,1045);
INSERT INTO TEAM_MEMBER
VALUES(1022,1046);
INSERT INTO TEAM_MEMBER
VALUES(1022,1047);
INSERT INTO TEAM_MEMBER
VALUES(1022,1048);
INSERT INTO TEAM_MEMBER
VALUES(1022,1049);

INSERT INTO VENUE
VALUES(1006, 4, 'Olympic Indoor Hall', 1);

INSERT INTO EVENT
VALUES(1007, 1, 1006, 0, '14-Aug-2004');

INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1016,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1032,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1033,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1034,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1035,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1020,1036,1,1);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1037,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1038,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1039,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1040,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1041,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1021,1042,2,2);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1044,3,3);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1045,3,3);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1046,3,3);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1047,3,3);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1048,3,3);
INSERT INTO SCOREBOARD
VALUES(4,1007,1022,1049,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(1007,1020,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1007,1021,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(1007,1022,'e');

----------------------------------------------------------------
-------------------End of 2004 Gymnastics-----------------------
----------------------------------------------------------------
----------------------------------------------------------------



-----------------------------------------------------------------------------------------------
-- BEIJING 2008
-- TENNIS - WOMEN'S DOUBLES
-- https://en.wikipedia.org/wiki/Tennis_at_the_2008_Summer_Olympics_%E2%80%93_Women%27s_doubles

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(103, 3, 'Olympic Green Tennis Centre', 1);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(103, 3, 103, 0, '17-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(110, 'Serena', 'Williams', 'United States', 'Saginaw, Michigan', '26-Sep-1981');
INSERT INTO PARTICIPANT
VALUES(111, 'Venus', 'Williams', 'United States', 'Lynwood, California', '17-Jun-1980');
INSERT INTO PARTICIPANT
VALUES(112, 'Anabel', 'Medina', 'Spain', 'Valencia, Spain', '31-Jul-1982');
INSERT INTO PARTICIPANT
VALUES(113, 'Virginia', 'Ruano', 'Spain', 'Madrid, Spain', '21-Sep-1973');
INSERT INTO PARTICIPANT
VALUES(114, 'Yan', 'Zi', 'China', 'Chengdu, Sichuan', '12-Nov-1984');
INSERT INTO PARTICIPANT
VALUES(115, 'Zheng', 'Jie', 'China', 'Chengdu, Sichuan', '05-Jul-1983');

INSERT INTO PARTICIPANT
VALUES(155, 'Tennis Coach', 'USA 2008', 'United States', 'United States', '01-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(156, 'Tennis Coach', 'ESP 2008', 'Spain', 'Spain', '02-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(157, 'Tennis Coach', 'CHN 2008', 'China', 'China', '03-Jul-1965');

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(170, 3, 'TEAM USA', 4, 3, 155);
INSERT INTO TEAM
VALUES(109, 3, 'TEAM ESP', 14, 3, 156);
INSERT INTO TEAM
VALUES(116, 3, 'TEAM CHN', 5, 3, 157);

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(170, 110);
INSERT INTO TEAM_MEMBER VALUES(170, 111);
INSERT INTO TEAM_MEMBER VALUES(109, 112);
INSERT INTO TEAM_MEMBER VALUES(109, 113);
INSERT INTO TEAM_MEMBER VALUES(116, 114);
INSERT INTO TEAM_MEMBER VALUES(116, 115);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(103, 170, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(103, 109, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(103, 116, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 103, 170, 110, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 103, 170, 111, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 103, 109, 112, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 103, 109, 113, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 103, 116, 114, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 103, 116, 115, 3,3);

-----------------------------------------------------------------------------------------------

----------------------------------------------------------------
-------------------Tennis doubles 2004-----------------------
----------------------------------------------------------------
----------------------------------------------------------------

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(203, 4, 'Olympic Tennis Centre', 1);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(203, 3, 203, 0, '22-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(144, 'Li', 'Ting', 'China', 'China', '05-Jan-1980');
INSERT INTO PARTICIPANT
VALUES(145, 'Sun', 'Tiantian', 'China', 'China', '12-Oct-1981');
INSERT INTO PARTICIPANT
VALUES(146, 'Conchita', 'Martinez', 'Spain', 'Spain', '16-Apr-1972');
INSERT INTO PARTICIPANT
VALUES(147, 'Paola', 'Suarez', 'Argentina', 'Argentina', '23-Jun-1976');
INSERT INTO PARTICIPANT
VALUES(148, 'Patricia', 'Tarabini', 'Argentina', 'Argentina', '06-Aug-1968');
-- coaches?

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(204, 4, 'TEAM CHN', 5, 3, null);
INSERT INTO TEAM
VALUES(205, 4, 'TEAM ESP', 14, 3, null);
INSERT INTO TEAM
VALUES(206, 4, 'TEAM ARG', 116, 3, null);
-- coach id?

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(204, 144);
INSERT INTO TEAM_MEMBER VALUES(204, 145);
INSERT INTO TEAM_MEMBER VALUES(205, 146);
INSERT INTO TEAM_MEMBER VALUES(205, 113);
INSERT INTO TEAM_MEMBER VALUES(206, 147);
INSERT INTO TEAM_MEMBER VALUES(206, 148);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(203, 204, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(203, 205, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(203, 206, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(4, 203, 204, 144, 1,1);
INSERT INTO SCOREBOARD
VALUES(4, 203, 204, 145, 1,1);
INSERT INTO SCOREBOARD
VALUES(4, 203, 205, 146, 2,2);
INSERT INTO SCOREBOARD
VALUES(4, 203, 205, 113, 2,2);
INSERT INTO SCOREBOARD
VALUES(4, 203, 206, 147, 3,3);
INSERT INTO SCOREBOARD
VALUES(4, 203, 206, 148, 3,3);

----------------------------------------------------------------
-------------------End of 2004 Tennis---------------------------
----------------------------------------------------------------
----------------------------------------------------------------




----------------------------------------------------------------
---------Men's light flyweight boxing 2004----------------------
----------------------------------------------------------------
----------------------------------------------------------------
INSERT INTO PARTICIPANT
VALUES(2001, 'Yan', 'Bartelemi', 'Cuba', 'Cuba', '05-Mar-1980');
INSERT INTO PARTICIPANT
VALUES(2002, 'Atagun', 'Yalcinkaya', 'Turkey', 'Turkey', '14-Dec-1986');
INSERT INTO PARTICIPANT
VALUES(2003, 'Zou', 'Shiming', 'China', 'China', '18-May-1981');
INSERT INTO PARTICIPANT
VALUES(2004, 'Sergey', 'Kazakov', 'Russia', 'Russia', '08-Jul-1976');

INSERT INTO TEAM
VALUES(2001, 4, 'Cuba', 3, 4, null);
INSERT INTO TEAM
VALUES(2002, 4, 'Turkey', 1006, 4, null);
INSERT INTO TEAM
VALUES(2003, 4, 'China', 5, 4, null);
INSERT INTO TEAM
VALUES(2004, 4, 'Russia', 10, 4, null);

INSERT INTO TEAM_MEMBER
VALUES(2001,2001);
INSERT INTO TEAM_MEMBER
VALUES(2002, 2002);
INSERT INTO TEAM_MEMBER
VALUES(2003,2003);
INSERT INTO TEAM_MEMBER
VALUES(2004,2004);

INSERT INTO VENUE
VALUES(2001, 4, 'Peristeri Olympic Boxing Hall', 1);

INSERT INTO EVENT
VALUES(2001, 4, 2001,1, '14-Aug-2004');

INSERT INTO SCOREBOARD
VALUES(4,2001,2001,2001,1,1);
INSERT INTO SCOREBOARD
VALUES(4,2001,2002,2002,2,2);
INSERT INTO SCOREBOARD
VALUES(4,2001,2003,2003,3,3);
INSERT INTO SCOREBOARD
VALUES(4,2001,2004,2004,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(2001,2001,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2001,2002,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2001,2003,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2001,2004,'e');

----------------------------------------------------------------
-------End of Men's light flyweight boxing 2004------------------
----------------------------------------------------------------
----------------------------------------------------------------



----------------------------------------------------------------
---------Men's 56kg weightlifting 2004--------------------------
----------------------------------------------------------------
----------------------------------------------------------------
INSERT INTO PARTICIPANT
VALUES(2005, 'Halil', 'Mutlu', 'Turkey', 'Turkey', '14-Jul-1973');
INSERT INTO PARTICIPANT
VALUES(2006, 'Wu', 'Meijin', 'China', 'China', '25-Apr-1980');
INSERT INTO PARTICIPANT
VALUES(2007, 'Sedat', 'Artuc', 'Turkey', 'Turkey', '09-Jun-1976');

INSERT INTO TEAM
VALUES(2005, 4, 'Turkey', 1001, 5, null);
INSERT INTO TEAM
VALUES(2006, 4, 'China', 5, 5, null);
INSERT INTO TEAM
VALUES(2007, 4, 'Turkey', 1001, 5, null);

INSERT INTO TEAM_MEMBER
VALUES(2005,2005);
INSERT INTO TEAM_MEMBER
VALUES(2006, 2006);
INSERT INTO TEAM_MEMBER
VALUES(2007,2007);

INSERT INTO VENUE
VALUES(2002, 4, 'Nikaia Olympic Weightlifting', 2);

INSERT INTO EVENT
VALUES(2002, 5, 2002,1, '14-Aug-2004');

INSERT INTO SCOREBOARD
VALUES(4,2002,2005,2005,1,1);
INSERT INTO SCOREBOARD
VALUES(4,2002,2006,2006,2,2);
INSERT INTO SCOREBOARD
VALUES(4,2002,2007,2007,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(2002,2005,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2002,2006,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2002,2007,'e');

----------------------------------------------------------------
-------End of Men's light flyweight boxing 2004------------------
----------------------------------------------------------------
----------------------------------------------------------------




----------------------------------------------------------------
---------Women's Individual Archery 2004------------------------
----------------------------------------------------------------
----------------------------------------------------------------
INSERT INTO PARTICIPANT
VALUES(2008, 'Park', 'Sung-hyun', 'South Korea', 'South Korea', '01-Jan-1983');
INSERT INTO PARTICIPANT
VALUES(2009, 'Lee', 'Sung-Jin', 'South Korea', 'South Korea', '07-Mar-1985');
INSERT INTO PARTICIPANT
VALUES(20010, 'Alison', 'Williamson', 'United Kingdom', 'Great Britain', '03-Nov-1971');

INSERT INTO TEAM
VALUES(2008, 4, 'South Korea', 8, 6, null);
INSERT INTO TEAM
VALUES(2009, 4, 'South Korea', 8, 6, null);
INSERT INTO TEAM
VALUES(2010, 4, 'Great Britain', 12, 6, null);

INSERT INTO TEAM_MEMBER
VALUES(2008,2008);
INSERT INTO TEAM_MEMBER
VALUES(2009, 2009);
INSERT INTO TEAM_MEMBER
VALUES(2010,20010);

INSERT INTO VENUE
VALUES(2003, 4, 'Panathenaic Stadium', 2);

INSERT INTO EVENT
VALUES(2003, 6, 2003,0, '14-Aug-2004');

INSERT INTO SCOREBOARD
VALUES(4,2003,2008,2008,1,1);
INSERT INTO SCOREBOARD
VALUES(4,2003,2009,2009,2,2);
INSERT INTO SCOREBOARD
VALUES(4,2003,2010,20010,3,3);

INSERT INTO EVENT_PARTICIPATION
VALUES(2003,2008,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2003,2009,'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(2003,2010,'e');

----------------------------------------------------------------
----------------------------------------------------------------
----------------------End of 2004 data--------------------------
----------------------------------------------------------------
----------------------------------------------------------------


----------------------------------------------------------------
----------------------------------------------------------------
----------------------Bejing data--------------------------
----------------------------------------------------------------
----------------------------------------------------------------


-- BEIJING 2008
-- GYMNASTICS ARTISTIC - WOMEN'S TEAM ALL-AROUND
-- https://en.wikipedia.org/wiki/Gymnastics_at_the_2008_Summer_Olympics
-- Tuesday, August 19, 2008 at Beijing National Indoor Stadium

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(101, 3, 'Beijing National Indoor', 3);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(101, 1, 101, 0, '19-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(101, 'Shawn', 'Johnson', 'United States', 'Des Moines, Iowa', '19-Jan-1992');
INSERT INTO PARTICIPANT
VALUES(102, 'Anastasia', 'Liukin', 'United States', 'Moscow, Russia', '30-Oct-1989');
INSERT INTO PARTICIPANT
VALUES(126, 'Chellsie', 'Memmel', 'United States', 'West Allis, Wisconsin', '23-Jan-1988');
INSERT INTO PARTICIPANT
VALUES(127, 'Samantha', 'Peszek', 'United States', 'Indianapolis, Indiana', '14-Dec-1991');
INSERT INTO PARTICIPANT
VALUES(128, 'Alicia', 'Sacramone', 'United States', 'Boston, Massachusetts', '03-Dec-1987');
INSERT INTO PARTICIPANT
VALUES(129, 'Bridget', 'Sloan', 'United States', 'Cincinnati', '23-Jun-1992');

INSERT INTO PARTICIPANT
VALUES(103, 'Cheng', 'Fei', 'China', 'Hubei, China', '29-May-1988');
INSERT INTO PARTICIPANT
VALUES(130, 'Deng', 'Linlin', 'China', 'Anhui, China', '21-Apr-1992');
INSERT INTO PARTICIPANT
VALUES(131, 'He', 'Kexin', 'China', 'Beijing, China', '01-Jan-1992');
INSERT INTO PARTICIPANT
VALUES(132, 'Jiang', 'Yuyuan', 'China', 'China', '01-Nov-1991');
INSERT INTO PARTICIPANT
VALUES(133, 'Li', 'Shanshan', 'China', 'Hubei, China', '22-Feb-1992');
INSERT INTO PARTICIPANT
VALUES(134, 'Yang', 'Yilin', 'China', 'China', '26-Aug-1992');

INSERT INTO PARTICIPANT
VALUES(135, 'Andreea', 'Acatrinei', 'Romania', 'Romania', '07-Apr-1992');
INSERT INTO PARTICIPANT
VALUES(136, 'Gabriela', 'Dragoi', 'Romania', 'Romania', '28-Aug-1992');
INSERT INTO PARTICIPANT
VALUES(137, 'Andreea', 'Grigore', 'Romania', 'Romania', '11-Apr-1991');
INSERT INTO PARTICIPANT
VALUES(138, 'Sandra', 'Izbasa', 'Romania', 'Romania', '18-Jun-1990');
INSERT INTO PARTICIPANT
VALUES(139, 'Steliana', 'Nistor', 'Romania', 'Romania', '15-Sep-1989');
INSERT INTO PARTICIPANT
VALUES(140, 'Anamaria', 'Tamarjan', 'Romania', 'Romania', '08-May-1991');

INSERT INTO PARTICIPANT
VALUES(149, 'Gymnastics Coach', 'USA 2008', 'United States', 'United States', '01-Apr-1965');
INSERT INTO PARTICIPANT
VALUES(150, 'Gymnastics Coach', 'CHN 2008', 'China', 'China', '02-Apr-1965');
INSERT INTO PARTICIPANT
VALUES(151, 'Gymnastics Coach', 'ROU 2008', 'Romania', 'Romania', '03-Apr-1965');

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(104, 3, 'TEAM USA', 4, 1, 149);
INSERT INTO TEAM
VALUES(105, 3, 'TEAM CHN', 5, 1, 150);
INSERT INTO TEAM
VALUES(115, 3, 'TEAM ROU', 15, 1, 151);
-- coach id's?

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(104, 101);
INSERT INTO TEAM_MEMBER VALUES(104, 102);
INSERT INTO TEAM_MEMBER VALUES(104, 126);
INSERT INTO TEAM_MEMBER VALUES(104, 127);
INSERT INTO TEAM_MEMBER VALUES(104, 128);
INSERT INTO TEAM_MEMBER VALUES(104, 129);

INSERT INTO TEAM_MEMBER VALUES(105, 103);
INSERT INTO TEAM_MEMBER VALUES(105, 130);
INSERT INTO TEAM_MEMBER VALUES(105, 131);
INSERT INTO TEAM_MEMBER VALUES(105, 132);
INSERT INTO TEAM_MEMBER VALUES(105, 133);
INSERT INTO TEAM_MEMBER VALUES(105, 134);

INSERT INTO TEAM_MEMBER VALUES(115, 135);
INSERT INTO TEAM_MEMBER VALUES(115, 136);
INSERT INTO TEAM_MEMBER VALUES(115, 137);
INSERT INTO TEAM_MEMBER VALUES(115, 138);
INSERT INTO TEAM_MEMBER VALUES(115, 139);
INSERT INTO TEAM_MEMBER VALUES(115, 140);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(101, 104, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(101, 105, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(101, 115, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 103, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 130, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 131, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 132, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 133, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 101, 105, 134, 1,1);

INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 101, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 102, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 126, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 127, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 128, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 101, 104, 129, 2,2);

INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 135, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 136, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 137, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 138, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 139, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 101, 115, 140, 3,3);

-----------------------------------------------------------------------------------------------
-- BEIJING 2008
-- ROWING - DOUBLE SCULLS WOMEN
-- https://en.wikipedia.org/wiki/Rowing_at_the_2008_Summer_Olympics
-- Gold: Georgina Evers-Swindell and Caroline Evers-Swindell (NZL)
-- Silver: Annekatrin Thiele and Christiane Huth (GER)
-- Bronze:Elise Laverick and Anna Bebington (GBR)

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(102, 3, 'Shunyi Olympic Rowing-Canoeing', 3);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(102, 1, 102, 0, '16-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(104, 'Georgina', 'Evers-Swindell', 'New Zealand', 'Hastings, New Zealand', '10-Oct-1978');
INSERT INTO PARTICIPANT
VALUES(105, 'Caroline', 'Evers-Swindell', 'New Zealand', 'Hastings, New Zealand', '10-Oct-1978');
INSERT INTO PARTICIPANT
VALUES(106, 'Annekatrin', 'Thiele', 'Germany', 'Saxony-Anhalt, Germany', '18-Oct-1984');
INSERT INTO PARTICIPANT
VALUES(107, 'Christiane', 'Huth', 'Germany', 'Suhl, Germany', '12-Sep-1980');
INSERT INTO PARTICIPANT
VALUES(108, 'Elise', 'Laverick', 'United Kingdom', 'Rustington, West Sussex, England', '27-Jul-1975');
INSERT INTO PARTICIPANT
VALUES(109, 'Anna', 'Bebington', 'United Kingdom', 'Leek, Staffordshire, England', '13-Feb-1983');

INSERT INTO PARTICIPANT
VALUES(152, 'Rowing Coach', 'NZL 2008', 'New Zealand','New Zealand', '01-Feb-1965');
INSERT INTO PARTICIPANT
VALUES(153, 'Rowing Coach', 'GER 2008', 'Germany', 'Germany', '02-Feb-1965');
INSERT INTO PARTICIPANT
VALUES(154, 'Rowing Coach', 'GBR 2008', 'United Kingdom', 'Great Britian', '03-Feb-1965');

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(106, 3, 'TEAM NZL', 106, 2, 152);
INSERT INTO TEAM
VALUES(107, 3, 'TEAM GER', 9, 2, 153);
INSERT INTO TEAM
VALUES(108, 3, 'TEAM GBR', 12, 2, 154);

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(106, 104);
INSERT INTO TEAM_MEMBER VALUES(106, 105);
INSERT INTO TEAM_MEMBER VALUES(107, 106);
INSERT INTO TEAM_MEMBER VALUES(107, 107);
INSERT INTO TEAM_MEMBER VALUES(108, 108);
INSERT INTO TEAM_MEMBER VALUES(108, 109);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(102, 106, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(102, 107, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(102, 108, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 102, 106, 104, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 102, 106, 105, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 102, 107, 106, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 102, 107, 107, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 102, 108, 108, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 102, 108, 109, 3,3);

-- BEIJING 2008
-- BOXING - MIDDLEWEIGHT
-- https://en.wikipedia.org/wiki/Boxing_at_the_2008_Summer_Olympics

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(104, 3, 'Workers Indoor Arena', 1);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(104, 4, 104, 1, '23-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(116, 'James', 'DeGale', 'United Kingdom', 'Hammersmith, West London', '03-Feb-1986');
INSERT INTO PARTICIPANT
VALUES(117, 'Emilio', 'Correa', 'Cuba', 'Cuba', '12-Oct-1985');
INSERT INTO PARTICIPANT
VALUES(118, 'Vijender', 'Singh', 'India', 'Kaluwas, Haryana', '29-Oct-1985');
INSERT INTO PARTICIPANT
VALUES(119, 'Darren', 'Sutherland', 'Ireland', 'Dublin, Ireland', '18-Apr-1982');
-- coaches?
INSERT INTO PARTICIPANT
VALUES(158, 'Boxing Coach', 'GBR 2008', 'United States', 'United States', '01-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(159, 'Boxing Coach', 'CUB 2008', 'Spain', 'Spain', '02-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(160, 'Boxing Coach', 'IND 2008', 'China', 'China', '03-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(161, 'Boxing Coach', 'IRL 2008', 'China', 'China', '03-Jul-1965');

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(117, 3, 'TEAM GBR', 12, 4, 158);
INSERT INTO TEAM
VALUES(110, 3, 'TEAM CUB', 3, 4, 159);
INSERT INTO TEAM
VALUES(111, 3, 'TEAM IND', 111, 4, 160);
INSERT INTO TEAM
VALUES(112, 3, 'TEAM IRL', 1001, 4, 161);
-- coach id?

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(117, 116);
INSERT INTO TEAM_MEMBER VALUES(110, 117);
INSERT INTO TEAM_MEMBER VALUES(111, 118);
INSERT INTO TEAM_MEMBER VALUES(112, 119);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(104, 117, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(104, 110, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(104, 111, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(104, 112, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 104, 117, 116, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 104, 110, 117, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 104, 111, 118, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 104, 112, 119, 3,3);

-----------------------------------------------------------------------------------------------
-- BEIJING 2008
-- WEIGHTLIFTING - Men's 69 kg
-- https://en.wikipedia.org/wiki/Weightlifting_at_the_2008_Summer_Olympics_%E2%80%93_Men%27s_69_kg

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(105, 3, 'Beihang University Gymnasium', 2);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(105, 5, 105, 1, '12-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(120, 'Lio', 'Hui', 'China', 'Hubei, China', '05-Oct-1987');
INSERT INTO PARTICIPANT
VALUES(121, 'Vencelas', 'Dabaya', 'France', 'Kumba, Cameroon', '28-Apr-1981');
INSERT INTO PARTICIPANT
VALUES(122, 'Yordanis', 'Borrero', 'Cuba', 'Havana, Cuba', '03-Jan-1978');

INSERT INTO PARTICIPANT
VALUES(162, 'Weightlifting Coach', 'CHN 2008', 'Spain', 'Spain', '02-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(163, 'Weightlifting Coach', 'FRA 2008', 'China', 'China', '03-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(164, 'Weightlifting Coach', 'CUB 2008', 'China', 'China', '03-Jul-1965');


-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(118, 3, 'TEAM CHN', 5, 5, 162);
INSERT INTO TEAM
VALUES(113, 3, 'TEAM FRA', 1005, 5, 163);
INSERT INTO TEAM
VALUES(119, 3, 'TEAM CUB', 3, 5, 164);

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(118, 120);
INSERT INTO TEAM_MEMBER VALUES(113, 121);
INSERT INTO TEAM_MEMBER VALUES(119, 122);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(105, 118, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(105, 113, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(105, 119, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 105, 118, 120, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 105, 113, 121, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 105, 119, 122, 3,3);

-----------------------------------------------------------------------------------------------
-- BEIJING 2008
-- ARCHERY
-- https://en.wikipedia.org/wiki/Archery_at_the_2008_Summer_Olympics

-- VENUE(venue id, olympic id, venue name, capacity)
INSERT INTO VENUE
VALUES(106, 3, 'Olympic Green Archery Field', 1);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(106, 6, 106, 0, '15-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(123, 'Zhang', 'Juanjuan', 'China', 'Shandong, People''s Republic of China', '02-Jan-1981');
INSERT INTO PARTICIPANT
VALUES(124, 'Park', 'Sung-hyun', 'South Korea', 'Incheon, Gyeonggi', '01-Jan-1983');
INSERT INTO PARTICIPANT
VALUES(125, 'Yun', 'Ok-Hee', 'South Korea', 'South Korea', '01-Mar-1985');

INSERT INTO PARTICIPANT
VALUES(165, 'Archery Coach', 'CHN 2008', 'China', 'China', '04-Jul-1965');
INSERT INTO PARTICIPANT
VALUES(166, 'Archery Coach', 'KOR 2008', 'South Korea', 'South Korea', '05-Jul-1965');

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(120, 3, 'TEAM CHN', 5, 6, 165);
INSERT INTO TEAM
VALUES(114, 3, 'TEAM KOR', 8, 6, 166);


-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(120, 123);
INSERT INTO TEAM_MEMBER VALUES(114, 124);
INSERT INTO TEAM_MEMBER VALUES(114, 125);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(106, 120, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(106, 114, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 106, 120, 123, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 106, 114, 124, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 106, 114, 125, 3,3);

----------------------------------------------------------------
----------------------------------------------------------------
----------------------Rowing 2004---------------------------
----------------------------------------------------------------
----------------------------------------------------------------


INSERT INTO VENUE
VALUES(202, 4, 'Schinias Olympic Rowing', 2);

-- EVENT(event id, sport id, venue id, gender, event time)
INSERT INTO EVENT
VALUES(202, 2, 202, 0, '15-Aug-2008');

-- PARTICIPANT(participant id, fname, lname, nationality, birth place, dob)
INSERT INTO PARTICIPANT
VALUES(141, 'Peggy', 'Waleska', 'Germany', 'Germany', '11-Apr-1980');
INSERT INTO PARTICIPANT
VALUES(142, 'Britta', 'Oppelt', 'Germany', 'Germany', '05-Jul-1978');
INSERT INTO PARTICIPANT
VALUES(143, 'Sarah', 'Winckless', 'United Kingdom', 'Great Britain', '18-Oct-1973');
-- coaches?

-- TEAM(team id, olympic id, team name, country id, sport id, coach id)
INSERT INTO TEAM
VALUES(201, 4, 'TEAM NZL', 106, 2, null);
INSERT INTO TEAM
VALUES(202, 4, 'TEAM GER', 9, 2, null);
INSERT INTO TEAM
VALUES(203, 4, 'TEAM GBR', 12, 2, null);
-- coach id?

-- TEAM_MEMBER(team id, participant id)
INSERT INTO TEAM_MEMBER VALUES(201, 104);
INSERT INTO TEAM_MEMBER VALUES(201, 105);
INSERT INTO TEAM_MEMBER VALUES(202, 141);
INSERT INTO TEAM_MEMBER VALUES(202, 142);
INSERT INTO TEAM_MEMBER VALUES(203, 143);
INSERT INTO TEAM_MEMBER VALUES(203, 108);

-- EVENT_PARTICIPATION(event id, team id, status)
INSERT INTO EVENT_PARTICIPATION
VALUES(202, 201, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(202, 202, 'e');
INSERT INTO EVENT_PARTICIPATION
VALUES(202, 203, 'e');

-- SCOREBOARD(olympic id, event id, team id, participant id, position, medal id)
INSERT INTO SCOREBOARD
VALUES(3, 202, 201, 104, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 202, 201, 105, 1,1);
INSERT INTO SCOREBOARD
VALUES(3, 202, 202, 141, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 202, 202, 142, 2,2);
INSERT INTO SCOREBOARD
VALUES(3, 202, 203, 143, 3,3);
INSERT INTO SCOREBOARD
VALUES(3, 202, 203, 108, 3,3);

----------------------------------------------------------------
-------------------End of 2004 Rowing---------------------------
----------------------------------------------------------------
----------------------------------------------------------------

commit;
purge recyclebin;