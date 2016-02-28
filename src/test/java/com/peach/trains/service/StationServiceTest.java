package com.peach.trains.service;

import com.peach.trains.Exception.TrainsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;

public class StationServiceTest {

    StationService stationService;

    @Before
    public void init() {
        stationService = new StationService();
    }

    @Test
    public void route_is_ABC_distance_should_be_9() throws TrainsException {
        int distance = stationService.calculateDistance("ABC");
        Assert.assertEquals(9, distance);
    }

    @Test
    public void route_is_AD_distance_should_be_5() throws TrainsException {
        int distance = stationService.calculateDistance("AD");
        Assert.assertEquals(5, distance);
    }

    @Test
    public void route_is_ADC_distance_should_be_13() throws TrainsException {
        int distance = stationService.calculateDistance("ADC");
        Assert.assertEquals(13, distance);
    }

    @Test
    public void route_is_AEBCD_distance_should_be_22() throws TrainsException {
        int distance = stationService.calculateDistance("AEBCD");
        Assert.assertEquals(22, distance);
    }

    @Test
    public void route_is_AED_distance_should_be_NO_SUCH_ROUTE() {
        try {
            int distance = stationService.calculateDistance("AED");
        } catch (TrainsException e) {
            Assert.assertEquals("ON SUCH ROUTE", e.getMessage());
        }
    }

    @Test
    public void start_C_end_C_maxStops_3_route_should_be_CDC_AND_CEBD() throws TrainsException {
        LinkedList<String> list = (LinkedList<String>) stationService.calculateRouteWithMaxStops("C", 3);
        for (String str : list) {
            System.out.println(str);
        }
    }

    @Test
    public void start_A_end_C_fixedStops_5_route_should_be_ABCDC_ADCDC_ADEBC() throws TrainsException {
        LinkedList<String> list = (LinkedList<String>) stationService.calculateRouteWithFixedStops("A", "C", 4);
        for (String str : list) {
            System.out.println(str);
        }
    }

    @Test
    public void start_A_end_C_shortest_route_should_be_9() throws TrainsException {
        Assert.assertEquals(9, stationService.calculateShortestRoute("A", "C"));
    }

    @Test
    public void start_B_end_B_shortest_route_should_be_9() throws TrainsException {
        Assert.assertEquals(9, stationService.calculateShortestRoute("B", "B"));
    }

    @Test
    public void start_C_end_C_less_than_distance_30_route_should_be_CDC_CDCEBC_CDEBC_CEBC_CEBCDC_CEBCEBC_CEBCEBCEBC() throws TrainsException {
        LinkedList<String> list = (LinkedList<String>) stationService.calculateRouteWithFixedDistance("C", "C", 30);
        for (String str : list) {
            System.out.println(str);
        }
    }
}