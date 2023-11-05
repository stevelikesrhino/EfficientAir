package com.ee542.iotgarbagecollector.database;


import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.util.annotation.NonNull;

@Table("nodes")
public record Node(
        @Id Long timestamp,
        Short location,
        Byte fill,
        Byte humidity
) {
}
