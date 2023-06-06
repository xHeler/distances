package com.kwiatkowsky.Distances.repositories;

import com.kwiatkowsky.Distances.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String> {
    City findByName(String name);
}