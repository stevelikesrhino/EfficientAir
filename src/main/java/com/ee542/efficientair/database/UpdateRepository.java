package com.ee542.efficientair.database;

import com.ee542.efficientair.entity.ThermostatHistory;
import com.ee542.efficientair.entity.ThermostatUpdate;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UpdateRepository extends ListCrudRepository<ThermostatHistory, Long> {
    @Query("SELECT * FROM thermostat_update")
    List<ThermostatUpdate> findAllItems();

    @Query("SELECT * FROM thermostat_update WHERE id = :selectId")
    List<ThermostatUpdate> getById(@Param("selectId") Short selectId);

    @Query("SELECT t.* FROM thermostat_update t INNER JOIN " +
            "(SELECT id, MAX(timestamp) as max_timestamp FROM thermostat_update GROUP BY id) as latest " +
            "ON t.id = latest.id AND t.timestamp = latest.max_timestamp")
    List<ThermostatUpdate> findLatestUpdatesForEachId();
}
