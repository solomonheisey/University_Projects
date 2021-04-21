import json
import math
import urllib

import pandas as pd


class Bike():
    station_info_dataset = pd.DataFrame()
    station_status_dataset = pd.DataFrame()

    def __init__(self, baseURL, station_info, station_status):
        # initialize the instance

        response = urllib.request.urlopen(baseURL + station_info)
        data = json.loads(response.read())
        self.station_info_dataset = pd.DataFrame(data['data']['stations'])

        response = urllib.request.urlopen(baseURL + station_status)
        data = json.loads(response.read())
        self.station_status_dataset = pd.DataFrame(data['data']['stations'])

    def total_bikes(self):

        # Creates a series for total number of bikes available
        bike_count = self.station_status_dataset['num_bikes_available']
        count = 0

        # Iterates through series and increments count based on value
        for bike in bike_count:
            count += bike

        return count

    def total_docks(self):

        # Creates a series for total number of docks available
        dock_count = self.station_status_dataset['num_docks_available']
        count = 0

        # Iterates through series and increments count based on value
        for dock in dock_count:
            count += dock

        return count

    def percent_avail(self, station_id):

        # Queries the data for matching station_id
        station = self.station_status_dataset[self.station_status_dataset.station_id == str(station_id)]

        # Checks if station_id was valid
        if station.empty:
            return ''
        else:

            # If station_id was valid then percent available is calculated
            percent = station.num_docks_available / (station.num_docks_available + station.num_bikes_available) * 100
            return str(math.floor(percent)) + "%"

    def closest_stations(self, latitude, longitude):

        # Calculates distance from each station and places result in dictionary where station id is key
        distance = {}
        for lat, lon, id in list(zip(self.station_info_dataset['lat'], self.station_info_dataset['lon'],
                                     self.station_info_dataset['station_id'])):
            distance[id] = self.distance(latitude, longitude, lat, lon)

        # Sorted in ascending order and selects top 3 closed stations
        sorted_distance = {k: v for k, v in sorted(distance.items(), key=lambda item: item[1])[:3]}

        # Iterates through sorted list and matches id to name, closest stations are placed in result dictionary
        result = {}
        for key in sorted_distance:
            for id, name in list(zip(self.station_info_dataset['station_id'], self.station_info_dataset['name'])):
                if key == id:
                    result[key] = name
                    break

        return result

    def closest_bike(self, latitude, longitude):

        # Calculates distance from each station and places result in dictionary where station id is key
        # if number of available bikes is >= 1 then it is added to dictionary
        distance = {}
        for lat, lon, id, available in list(zip(self.station_info_dataset['lat'], self.station_info_dataset['lon'],
                                                self.station_info_dataset['station_id'],
                                                self.station_status_dataset['num_bikes_available'])):
            if available >= 1:
                distance[id] = self.distance(latitude, longitude, lat, lon)

        # Sorted in ascending order and selects closest station with at least 1 bike
        sorted_distance = {k: v for k, v in sorted(distance.items(), key=lambda item: item[1])[:1]}

        # Iterates through sorted list and matches id to name, closest stations are placed in result dictionary
        result = {}
        for key in sorted_distance:
            for id, name in list(zip(self.station_info_dataset['station_id'], self.station_info_dataset['name'])):
                if key == id:
                    result[key] = name
                    break

        return result

    def station_bike_avail(self, latitude, longitude):

        # Iterates through dataset and checks for matches on coordinates. If there is a match then station_id is saved
        station_id = -1
        for lat, lon, id in list(zip(self.station_info_dataset['lat'], self.station_info_dataset['lon'],
                                     self.station_info_dataset['station_id'])):
            if latitude == lat and longitude == lon:
                station_id = id

        # Edge case where station at given coordinates does not exist
        if station_id == -1:
            return {}

        # Matches station id to number of available bikes, result is placed in dictionary
        result = {}
        for id, available in list(
                zip(self.station_info_dataset['station_id'], self.station_status_dataset['num_bikes_available'])):
            if id == station_id:
                result[station_id] = available
                break

        return result

    def distance(self, lat1, lon1, lat2, lon2):
        p = 0.017453292519943295
        a = 0.5 - math.cos((lat2 - lat1) * p) / 2 + math.cos(lat1 * p) * math.cos(lat2 * p) * (
                1 - math.cos((lon2 - lon1) * p)) / 2
        return 12742 * math.asin(math.sqrt(a))


if __name__ == '__main__':
    instance = Bike('https://api.nextbike.net/maps/gbfs/v1/nextbike_pp/en', '/station_information.json',
                    '/station_status.json')
    print('------------------total_bikes()-------------------')
    t_bikes = instance.total_bikes()
    print(type(t_bikes))
    print(t_bikes)
    print()

    print('------------------total_docks()-------------------')
    t_docks = instance.total_docks()
    print(type(t_docks))
    print(t_docks)
    print()

    print('-----------------percent_avail()------------------')
    p_avail = instance.percent_avail(342885)  # replace with station ID
    print(type(p_avail))
    print(p_avail)
    print()

    print('----------------closest_stations()----------------')
    c_stations = instance.closest_stations(40.444618, -79.954707)  # replace with latitude and longitude
    print(type(c_stations))
    print(c_stations)
    print()

    print('-----------------closest_bike()-------------------')
    c_bike = instance.closest_bike(40.444618, -79.954707)  # replace with latitude and longitude
    print(type(c_bike))
    print(c_bike)
    print()

    print('---------------station_bike_avail()---------------')
    s_bike_avail = instance.station_bike_avail(40.445834,
                                               -79.954707)  # replace with exact latitude and longitude of station
    print(type(s_bike_avail))
    print(s_bike_avail)
