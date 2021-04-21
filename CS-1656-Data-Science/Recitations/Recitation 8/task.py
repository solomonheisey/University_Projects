import sqlite3 as lite
import csv
import re
import pandas as pd


class Task(object):
    def __init__(self, db_name):
        self.con = lite.connect(db_name)
        self.cur = self.con.cursor()

    #q0 is an example 
    def q0(self):
        query = '''
            SELECT COUNT(*) FROM students
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows
    
    def q1(self):
        query = '''
            SELECT firstName, lastName
            FROM students
            WHERE yearStarted = 2019
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows
        

    def q2(self):
        query = '''
            SELECT s.firstName, s.lastName
            FROM students s 
            JOIN majors m on s.sid == m.sid
            WHERE m.major == 'CS' or m.major == 'COE'
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows

    def q3(self):
        query = '''
            SELECT COUNT(*)
            FROM students s 
            JOIN majors m on s.sid == m.sid
            WHERE m.major == 'ASTRO'
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows


    def q4(self):
        query = '''
            SELECT s.firstName, s.lastName, s.yearStarted, COUNT(g.credits)
            FROM students s
            JOIN grades g on s.sid == g.sid
            WHERE grade != 0
            GROUP BY s.sid
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows

    def q5(self):
        query = '''
            SELECT professor, COUNT(number) 
            FROM Courses
            GROUP BY professor
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows

    def q6(self):
        query = '''
            SELECT g.cid,
                g.grade, 
                COUNT(g.grade)
            FROM Grades g 
            GROUP BY g.cid, g.grade
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows

    def q7(self):
        query = '''
            SELECT c.cid,
                g.grade, 
                SUM(CASE WHEN g.grade == 4 THEN 1 ELSE 0 END) as four
            FROM Courses c 
            JOIN Grades g on c.cid == g.cid
            WHERE g.grade == 4
            GROUP BY c.cid
        '''
        self.cur.execute(query)
        all_rows = self.cur.fetchall()
        return all_rows

if __name__ == "__main__":
    task = Task("students.db")
    rows = task.q0()
    print(rows)
    print()
    rows = task.q1()
    print(rows)
    print()
    rows = task.q2()
    print(rows)
    print()
    rows = task.q3()
    print(rows)
    print()
    rows = task.q4()
    print(rows)
    print()
    rows = task.q5()
    print(rows)
    print()
    rows = task.q6()
    print(rows)
    print()
    rows = task.q7()
    print(rows)
    print()
