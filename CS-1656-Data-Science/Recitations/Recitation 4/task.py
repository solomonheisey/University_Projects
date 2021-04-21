import json
from datetime import datetime, timedelta
import requests
import pandas as pd
import numpy as np
from sklearn import linear_model, tree, metrics
import ssl

ssl._create_default_https_context = ssl._create_unverified_context


class Task(object):
    def __init__(self, bike_df, bank_df):
        np.random.seed(31415)
        self.bike_data = bike_df.sample(1000).copy()
        self.bank_data = bank_df.copy()

    def t1(self):
        train = self.bike_data.iloc[1:900]
        train_x = train[['weekday']].values
        train_y = train[['cnt']].values

        test = self.bike_data.iloc[901:]
        test_x = test[['weekday']].values
        test_y = test[['cnt']].values

        # Create linear regression object
        regr = linear_model.LinearRegression()

        # Train the model using the training sets
        regr.fit(train_x, train_y)

        predict_y = regr.predict(test_x)

        meansq_error = np.mean((predict_y - test_y) ** 2)

        return meansq_error

    def t2_1(self):
        train = self.bike_data.iloc[1:900]
        train_x = train[
            ['weekday', 'season', 'hr', 'holiday', 'workingday', 'weathersit', 'temp', 'temp_feels', 'hum',
             'windspeed']].values
        train_y = train[['cnt']].values

        test = self.bike_data.iloc[901:]
        test_x = test[['weekday', 'season', 'hr', 'holiday', 'workingday', 'weathersit', 'temp', 'temp_feels', 'hum',
                       'windspeed']].values
        test_y = test[['cnt']].values

        # Create linear regression object
        regr = linear_model.LinearRegression()

        # Train the model using the training sets
        regr.fit(train_x, train_y)

        predict_y = regr.predict(test_x)

        meansq_error = np.mean((predict_y - test_y) ** 2)

        return meansq_error

    def t3(self):
        self.bank_data['mortgage'] = self.bank_data['mortgage'].replace(['NO', 'YES'], [1, 2])
        self.bank_data['married'] = self.bank_data['married'].replace(['NO', 'YES'], [1, 2])
        self.bank_data['sex'] = self.bank_data['sex'].replace(['MALE', 'FEMALE'], [1, 2])
        self.bank_data['region'] = self.bank_data['region'].replace(['INNER_CITY', 'TOWN', 'RURAL', 'SUBURBAN'],
                                                                    [1, 2, 3, 4])
        train = self.bank_data[1:500]

        train_x = train[['region', 'sex', 'married']].values
        train_y = train[['mortgage']].values

        test = self.bank_data[501:]
        test_x = test[['region', 'sex', 'married']].values
        test_y = test[['mortgage']].values

        clf = tree.DecisionTreeClassifier()
        clf = clf.fit(train_x, train_y)

        predict_y = clf.predict(test_x)

        accuracy = metrics.accuracy_score(test_y, predict_y)
        return accuracy


if __name__ == "__main__":
    t = Task(pd.read_csv('http://data.cs1656.org/bike_share.csv'), pd.read_csv('http://data.cs1656.org/bank-data.csv'))
    print("---------- Task 1 ----------")
    print(t.t1())
    print("--------- Task 2.1 ---------")
    print(t.t2_1())
    print("---------- Task 3 ----------")
    print(t.t3())
