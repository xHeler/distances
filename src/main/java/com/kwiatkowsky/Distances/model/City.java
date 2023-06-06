package com.kwiatkowsky.Distances.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "cities")
public class City {
    @Id
    private String id;
    private String name;
    private double lat;
    private double lng;
}
