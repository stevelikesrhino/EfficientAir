package com.ee542.iotgarbagecollector.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("locations")
public record Location(
        @Id String location,
        String latitude,
        String longitude,
        String name,
        String address,
        String post
){}
