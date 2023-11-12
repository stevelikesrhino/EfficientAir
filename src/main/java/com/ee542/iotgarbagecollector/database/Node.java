package com.ee542.iotgarbagecollector.database;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

@Table("nodes")
public record Node(
        @Id
        @JsonProperty("timeStamp")
        Long time,
        Short location,
        Byte fill,
        Byte humidity
) {
}
