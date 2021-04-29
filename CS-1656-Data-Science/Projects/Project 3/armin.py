import csv
import itertools

from pandas import DataFrame


class Armin:

    def apriori(self, input_filename, output_filename, min_support_percentage, min_confidence):
        transactions = {}

        # Opens desired csv file
        with open(input_filename, 'r') as csv_file:
            csv_reader = csv.reader(csv_file)

            # Parses each row and creates dictionary where transaction id is the key and values are added as a list
            for row in csv_reader:
                i = 0
                key = -1
                values = []
                for cell in row:
                    if i == 0:
                        key = cell
                    else:
                        values.append(cell)
                    i += 1
                transactions[key] = values

        # Parsed data is converted into pandas dataframe object
        df = DataFrame.from_dict(transactions, orient='index')

        # Dataframe is iterated over and occurrences of each value is calculated and stored in dictionary
        support_counts = {}
        total_transactions = 0
        for row in list(df.values):
            for value in row:
                if value not in support_counts.keys():
                    if value is not None:
                        support_counts[value] = 1
                else:
                    support_counts[value] += 1
            total_transactions += 1

        # Support percentage is created and if support percentage >= min support percentage then it is added to vfi
        vfi = {}
        for key, value in support_counts.items():
            support_percentage = value / total_transactions
            if support_percentage >= min_support_percentage:
                vfi[key] = (support_percentage, value)

        # List contains all possible combinations from 1 to vfi.keys() + 1
        new_vfi = {}
        for num in range(1, len(vfi.keys()) + 1):
            for combination in itertools.combinations(vfi.keys(), num):
                if len(combination) == 1:
                    new_vfi[combination[0]] = vfi[combination[0]][0]
                else:
                    support = calculate_support(combination, df, total_transactions)
                    if support >= min_support_percentage:
                        new_vfi[combination] = support

        # List contains all possible combinations
        combo_list = []
        for combo in new_vfi.keys():
            combo_list.append(combo)

        r_output = []
        i = 0
        while i < len(combo_list):
            j = 0
            while j < len(combo_list):

                # Makes sure left and right had side do not contain the same element(s)
                check = False
                for x in combo_list[i]:
                    for y in combo_list[j]:
                        if x == y:
                            check = True

                if combo_list[i] != combo_list[j] and not check:

                    # Creates unique list which stores union of X and Y
                    union = [combo_list[i]] + [combo_list[j]]
                    unique = []
                    for u in union:
                        if len(u) > 1:
                            for u2 in u:
                                unique.append(u2)
                        else:
                            unique.append(u)

                    # Calculates support of X
                    support_x = calculate_support(combo_list[i], df, total_transactions)

                    # Calculates support of X U Y
                    support_y = calculate_support(unique, df, total_transactions)

                    # Calculates confidence
                    confidence = support_y / support_x

                    # Set added to output if confidence and support meet thresholds
                    if confidence >= min_confidence and support_y >= min_support_percentage:
                        r_output.append([support_y, confidence, combo_list[i], combo_list[j]])
                j += 1
            i += 1

        # Writes output to target file
        with open(output_filename, mode='w') as output_file:

            # Output for S rows
            for keys, value in new_vfi.items():
                key = ""
                keys = sorted(keys)
                for k in keys:
                    key += k
                    key += ','
                key = key[:-1]

                output_file.write("S,%s,%s\n" % (format(value, '.4f'), key))

            # Output for R rows
            for data in r_output:
                left = ""
                for v in data[2]:
                    left += v
                    left += ','
                left = left[:-1]

                right = ""
                for v in data[3]:
                    right += v
                    right += ','
                right = right[:-1]

                output_file.write("R,%s,%s,%s,'=>',%s\n" % (format(data[0], '.4f'), format(data[1], '.4f'), left, right))


# Calculates support of given list
def calculate_support(combination, df, total_transactions):
    occurrences = 0
    for row in list(df.values):
        if set(combination).issubset(set(row)):
            occurrences += 1
    return occurrences / total_transactions


if __name__ == "__main__":
    armin = Armin()
    armin.apriori('input.csv', 'output.sup=0.5,conf=0.7.csv', 0.5, 0.7)
    armin.apriori('input.csv', 'output.sup=0.5,conf=0.8.csv', 0.5, 0.8)
    armin.apriori('input.csv', 'output.sup=0.6,conf=0.8.csv', 0.6, 0.8)
