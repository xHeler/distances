package com.kwiatkowsky.Distances;
import com.kwiatkowsky.Distances.controllers.CityController;
import com.kwiatkowsky.Distances.model.City;
import com.kwiatkowsky.Distances.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CityControllerTest {

    private CityController cityController;

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        cityController = new CityController(cityRepository);
    }

    @Test
    public void testGetAllCities() {
        // Mock data
        List<City> cities = new ArrayList<>();
        cities.add(City.builder().name("City1").build());
        cities.add(City.builder().name("City2").build());

        // Mock the behavior of the repository
        when(cityRepository.findAll()).thenReturn(cities);

        // Call the method in the controller
        List<City> result = cityController.getAllCities();

        // Verify the result
        assertEquals(cities, result);
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    public void testGetCity() {
        // Mock data
        City city = City.builder().name("City1").build();

        // Mock the behavior of the repository
        when(cityRepository.findByName("City1")).thenReturn(city);

        // Call the method in the controller
        City result = cityController.getCity("City1");

        // Verify the result
        assertEquals(city, result);
        verify(cityRepository, times(1)).findByName("City1");
    }

    @Test
    public void testGenerateMap() {
        // Mock data
        int n = 5;
        List<City> cities = new ArrayList<>();
        cities.add(City.builder().name("City1").build());
        cities.add(City.builder().name("City2").build());

        // Call the method in the controller
        List<City> result = cityController.generateMap(n);

        // Verify the result
        assertEquals(n, result.size());
        verify(cityRepository, times(1)).deleteAll();
        verify(cityRepository, times(1)).saveAll(result);
    }

    @Test
    public void testGetDistance() {
        // Mock data
        City cityA = City.builder().name("City1").build();
        cityA.setLat(40.7128);
        cityA.setLng(-74.0060);

        City cityB = City.builder().name("City2").build();
        cityB.setLat(34.0522);
        cityB.setLng(-118.2437);

        // Mock the behavior of the repository
        when(cityRepository.findByName("City1")).thenReturn(cityA);
        when(cityRepository.findByName("City2")).thenReturn(cityB);

        // Call the method in the controller
        double result = cityController.getDistance("City1", "City2");

        // Verify the result
        assertEquals(3935.74, result, 0.2);
        verify(cityRepository, times(1)).findByName("City1");
        verify(cityRepository, times(1)).findByName("City2");
    }
}
