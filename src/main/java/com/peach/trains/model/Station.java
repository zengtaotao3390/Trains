package com.peach.trains.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by y400 on 2016/2/21.
 */
public enum Station {
    A, B, C, D, E;

    public enum NeighbourStation{
        AB(A, B, 5), AD(A, D, 5),
        AE(A, E, 7), BC(B, C, 4),
        CD(C, D, 8), CE(C, E, 2),
        DC(D, C, 8), DE(D, E, 6),
        EB(E, B, 3);

        private final Station startStation;
        private final Station nextStation;
        private final int distance;

        private NeighbourStation(Station startStation, Station endStation, int distance){
            this.startStation = startStation;
            this.nextStation = endStation;
            this.distance = distance;
        }

        private static final Map<Station, Map<Station, NeighbourStation>> trainStationMapEnumMap =
                new EnumMap<Station, Map<Station, NeighbourStation>>(Station.class);
        static{
            for(Station trainStation : Station.values()) {
                trainStationMapEnumMap.put(trainStation, new EnumMap<Station, NeighbourStation>(Station.class));
            }
            for(NeighbourStation neighbourStation : NeighbourStation.values()) {
                trainStationMapEnumMap.get(neighbourStation.startStation).put(neighbourStation.nextStation, neighbourStation);
            }
        }

        public static NeighbourStation getNeighbourStation(Station startStation, Station endStation) {
            return trainStationMapEnumMap.get(startStation).get(endStation);
        }

        public static Map<Station, NeighbourStation> getNextStationMap(Station startStation) {
            return trainStationMapEnumMap.get(startStation);
        }

        public int getDistance(){
            return distance;
        }
    }




}
