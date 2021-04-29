import csv
import json
import ssl

from requests import get

ssl._create_default_https_context = ssl._create_unverified_context


class Task(object):
    def __init__(self):
        self.response = get('http://db.cs.pitt.edu/courses/cs1656/data/hours.json', verify=False)
        self.hours = json.loads(self.response.content)

    def part4(self):
        with open('hours.csv', mode='w', newline='') as hours:
            fields = ['name', 'day', 'time']
            writer = csv.DictWriter(hours, fieldnames=fields)
            writer.writeheader()

            for attributes in self.hours:
                writer.writerow(attributes)

    def part5(self):
        f = open('part5.txt', 'w')

        with open('hours.csv', "rt", encoding='ascii') as hours:
            for row in hours:
                f.write(row)

    def part6(self):

        f = open('part6.txt', 'w')
        with open('hours.csv', newline='') as file:
            reader = csv.reader(file)
            for row in reader:
                f.write("%s" % row)

    def part7(self):

        f = open('part7.txt', 'w')
        with open('hours.csv', newline='') as file:
            reader = csv.reader(file)
            for row in reader:
                for cell in row:
                    f.write("%s" % cell)


if __name__ == '__main__':
    task = Task()
    task.part4()
    task.part5()
    task.part6()
    task.part7()
