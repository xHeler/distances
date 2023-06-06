package com.kwiatkowsky.Distances.controllers;

import com.kwiatkowsky.Distances.model.City;
import com.kwiatkowsky.Distances.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
public class CityController {
    private final CityRepository cityRepository;

    @GetMapping()
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @GetMapping("/city/{name}")
    public City getCity(@PathVariable String name) {
        return cityRepository.findByName(name);
    }

    @PostMapping("/generateMap/{n}")
    public List<City> generateMap(@PathVariable int n) {
        Faker faker = new Faker();
        Random random = new Random();
        cityRepository.deleteAll();

        List<City> cities = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            City city = City.builder().name(faker.address().cityName()).build();
            city.setLat(random.nextDouble() * 180 - 90);  // lat between -90 and 90
            city.setLng(random.nextDouble() * 360 - 180); // lng between -180 and 180
            cities.add(city);
        }

        cityRepository.saveAll(cities);

        return cities;
    }

    @GetMapping("/distance/{city1}/{city2}")
    public double getDistance(@PathVariable String city1, @PathVariable String city2) {
        City cityA = cityRepository.findByName(city1);
        City cityB = cityRepository.findByName(city2);
        if (cityA == null || cityB == null) return -1;

        final int R = 6371; // Radius of the earth in km

        double latDistance = Math.toRadians(cityB.getLat() - cityA.getLat());
        double lonDistance = Math.toRadians(cityB.getLng() - cityA.getLng());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(cityA.getLat())) * Math.cos(Math.toRadians(cityB.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
