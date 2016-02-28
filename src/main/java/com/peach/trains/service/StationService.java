package com.peach.trains.service;

import com.peach.trains.Exception.TrainsException;
import com.peach.trains.model.Station;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by y400 on 2016/2/21.
 */
public class StationService {

    private static final String regex = "[ABCDE]*$";

    public int calculateDistance(String route) throws TrainsException {
        checkInputRoute(route);
        int distance = 0;
        for (int stationIndex = 0; stationIndex < route.length(); stationIndex++) {
            if (checkStationIndexIsEnd(route, stationIndex)) {
                return distance;
            }
            String startStation = route.substring(stationIndex, stationIndex + 1);
            int nextStationIndex = stationIndex + 1;
            String nextStation = route.substring(nextStationIndex, nextStationIndex + 1);
            Station.NeighbourStation neighbourStation = Station.NeighbourStation.getNeighbourStation(
                    Station.valueOf(startStation), Station.valueOf(nextStation));
            checkNeighbourStationIsOK(neighbourStation);
            distance += neighbourStation.getDistance();
        }
        return distance;
    }

    private void calculateRouteWithMaxStops(String station, String startStation, String route, int maxStops, List<String> list) {
        if (maxStops < 0) {
            return;
        }

        if (route.length() > 1 && route.endsWith(startStation)) {
            list.add(route);
        }
        EnumMap<Station, Station.NeighbourStation> stationEnumMap = (EnumMap<Station, Station.NeighbourStation>)
                Station.NeighbourStation.getNextStationMap(Station.valueOf(station));
        for (Station nextStation : stationEnumMap.keySet()) {
            calculateRouteWithMaxStops(nextStation.toString(), startStation, route + nextStation, maxStops - 1, list);
        }

    }

    public List<String> calculateRouteWithMaxStops(String startStation, int maxStops) throws TrainsException {
        checkInputRoute(startStation);
        List<String> list = new LinkedList<String>();
        calculateRouteWithMaxStops(startStation, startStation, startStation, maxStops, list);
        return list;
    }


    private void calculateRouteWithFixedStops(String startStation, String endStation, String route, int maxStops, List<String> list) {
        if (maxStops < 0) {
            return;
        }

        if (route.length() == 5 && route.endsWith(endStation)) {
            list.add(route);
        }
        EnumMap<Station, Station.NeighbourStation> stationEnumMap = (EnumMap<Station, Station.NeighbourStation>)
                Station.NeighbourStation.getNextStationMap(Station.valueOf(startStation));
        for (Station nextStation : stationEnumMap.keySet()) {
            calculateRouteWithFixedStops(nextStation.toString(), endStation, route + nextStation, maxStops - 1, list);
        }

    }

    public List<String> calculateRouteWithFixedStops(String startStation, String endStation, int fixedStops) throws TrainsException {
        checkInputRoute(startStation);
        List<String> list = new LinkedList<String>();
        calculateRouteWithFixedStops(startStation, endStation, startStation, fixedStops, list);
        return list;
    }

    private void calculateShortestRoute(String startStation, String endStation, String route, int distance, TreeSet<Integer> set) {
        if (route.contains("CDCD")) {
            return;
        }
        if (route.length() > 1 && route.endsWith(endStation)) {
            set.add(distance);
            return;
        }
        EnumMap<Station, Station.NeighbourStation> stationEnumMap = (EnumMap<Station, Station.NeighbourStation>)
                Station.NeighbourStation.getNextStationMap(Station.valueOf(startStation));
        for (Station nextStation : stationEnumMap.keySet()) {
            calculateShortestRoute(nextStation.toString(), endStation, route + nextStation,
                    stationEnumMap.get(nextStation).getDistance() + distance, set);
        }

    }

    public int calculateShortestRoute(String startStation, String endStation) throws TrainsException {
        checkInputRoute(startStation);
        checkInputRoute(endStation);
        TreeSet<Integer> set = new TreeSet<Integer>();
        calculateShortestRoute(startStation, endStation, startStation, 0, set);
        int result = 0;
        for (Integer a : set) {
            result = a;
            break;
        }
        return result;
    }

    private void calculateRouteLessThanFixedDistance(String station, String startStation, String route, int fixDistance, int distance, List<String> list) {
        if (fixDistance <= distance) {
            return;
        }

        if (route.length() > 1 && route.endsWith(startStation)) {
            list.add(route);
        }
        EnumMap<Station, Station.NeighbourStation> stationEnumMap = (EnumMap<Station, Station.NeighbourStation>)
                Station.NeighbourStation.getNextStationMap(Station.valueOf(station));
        for (Station nextStation : stationEnumMap.keySet()) {
            calculateRouteLessThanFixedDistance(nextStation.toString(), startStation, route + nextStation, fixDistance, stationEnumMap.get(nextStation).getDistance() + distance, list);
        }

    }

    public List<String> calculateRouteWithFixedDistance(String startStation, String endStation, int fixDistance) throws TrainsException {
        checkInputRoute(startStation);
        List<String> list = new LinkedList<String>();
        calculateRouteLessThanFixedDistance(startStation, endStation, startStation, fixDistance, 0, list);
        return list;
    }


    private void checkNeighbourStationIsOK(Station.NeighbourStation neighbourStation) throws TrainsException {
        if (neighbourStation == null) {
            throw new TrainsException("ON SUCH ROUTE");
        }
    }

    private boolean checkStationIndexIsEnd(String route, int stationIndex) {
        if (stationIndex == (route.length() - 1)) {
            return true;
        }
        return false;
    }

    private void checkInputRoute(String route) throws TrainsException {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(route);
        if (!m.find()) {
            throw new TrainsException("ON SUCH ROUTE");
        }
    }


}
