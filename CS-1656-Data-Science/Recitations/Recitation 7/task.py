import json
from datetime import datetime, timedelta
import requests
import pandas as pd
import numpy as np
from scipy.spatial.distance import euclidean, cityblock, cosine
import ssl

ssl._create_default_https_context = ssl._create_unverified_context


class Task(object):
    def __init__(self, data):
        self.df = pd.read_csv(data)

    def t1(self, name):

        df = self.df.copy()

        sim_weights = {}
        for user in df.columns[1:]:
            df_subset = df[[str(name), user]][df[str(name)].notnull() & df[user].notnull()]
            sim_weights[user] = 1 / (1 + cosine(df_subset[name].values.flatten(), df_subset[user].values.flatten()))

        df = df[df[name].isna()]
        del df[name]

        result = []

        for i in range(len(df)):
            ratings = df.iloc[i][1:]

            predicted_rating = 0.0
            weights_sum = 0.0
            for user in df.columns[1:]:
                if not np.isnan(sim_weights[user]) and not np.isnan(ratings[user]):
                    predicted_rating += ratings[user] * sim_weights[user]
                    weights_sum += sim_weights[user]

            predicted_rating /= weights_sum
            result.append((df.iloc[i][0], predicted_rating))

        return result

    def t2(self, name):
        df = self.df.copy().transpose()

        weights = {}
        for i in range(len(df.columns)):
            j = 0
            while j in range(len(df.columns)):
                if i != j:
                    df_subset = df[[i, j]][df[i].notnull() & df[j].notnull()]
                    weights[i, j] = 1 / (1 + cosine(pd.to_numeric(df_subset[i].values.flatten()[1:]),
                                                    pd.to_numeric(df_subset[j].values.flatten()[1:])))
                j += 1
            i += 1

        sim_weights = {}
        key_list = []
        for key, value in weights.items():
            if (key[0], key[1]) not in key_list and (key[1], key[0]) not in key_list:
                key_list.append(key)
                sim_weights[key] = value

        user_col = df.loc[name]

        user_null_movies = []
        user_valid_movies = []
        for key, value in user_col.items():
            if pd.isna(value):
                user_null_movies.append(key)
            else:
                user_valid_movies.append(key)

        return []

    def t3(self, name):

        df = self.df.copy()

        sim_weights = {}
        for user in df.columns[1:]:
            df_subset = df[[str(name), user]][df[str(name)].notnull() & df[user].notnull()]
            sim_weights[user] = 1 / (1 + cosine(df_subset[name].values.flatten(), df_subset[user].values.flatten()))

        del sim_weights[name]
        sim_weights = dict(sorted(sim_weights.items(), key=lambda item: item[1], reverse=True)[:10])

        df = df[df[name].isna()]
        del df[name]

        result = []

        for i in range(len(df)):
            ratings = df.iloc[i][1:]

            predicted_rating = 0.0
            weights_sum = 0.0
            for user in sim_weights.keys():
                if not np.isnan(sim_weights[user]) and not np.isnan(ratings[user]):
                    print(ratings[user])
                    predicted_rating += ratings[user] * sim_weights[user]
                    weights_sum += sim_weights[user]

            predicted_rating /= weights_sum
            result.append((df.iloc[i][0], predicted_rating))

        return result


if __name__ == "__main__":
    # using the class movie ratings data we collected in http://data.cs1656.org/movie_class_responses.csv
    t = Task('http://data.cs1656.org/movie_class_responses.csv')
    print(t.t1('BabyKangaroo'))
    print('------------------------------------')
    print(t.t2('BabyKangaroo'))
    print('------------------------------------')
    print(t.t3('BabyKangaroo'))
    print('------------------------------------')
