package com.ee542.iotgarbagecollector.database;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface LocationRepository extends ListCrudRepository<Location, String> {
    @Query("SELECT * FROM locations")
    List<Location> findAllItems();
}
