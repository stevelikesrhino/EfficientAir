package com.ee542.iotgarbagecollector.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Table("nodes")
public record Node(
        @Id
        @JsonProperty("time_stamp")
        Long time,
        Short location,
        Byte fill,
        Byte humidity
) {
}
