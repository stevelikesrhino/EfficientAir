package com.ee542.efficientair.database;

import com.ee542.efficientair.entity.ThermostatHistory;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThermostatHistoryRepository extends ListCrudRepository<ThermostatHistory, Long> {
    @Query("SELECT * FROM thermostat_history")
    List<ThermostatHistory> findAllItems();

    @Query("SELECT * FROM thermostat_history WHERE timestamp >= :cutoffTimestamp")
    List<ThermostatHistory> getByTime(@Param("cutoffTimestamp") Long cutoffTimestamp);

    @Query("SELECT * FROM thermostat_history WHERE id = :selectId")
    List<ThermostatHistory> getById(@Param("selectId") Short selectId);

    @Query("SELECT * FROM thermostat_history WHERE id == :selectId and timestamp >= :cutoffTimestamp")
    List<ThermostatHistory> getByIdTime(@Param("selectId") Short selectId,
                                      @Param("cutoffTimestamp") Long cutoffTimestamp);

    @Query("SELECT * FROM thermostat_history th " +
            "WHERE th.id = :selectId " +
            "AND th.timestamp > (SELECT MAX(th2.timestamp) - 43200 FROM thermostat_history th2 WHERE th2.id = :selectId) " +
            "AND th.timestamp <= (SELECT MAX(th2.timestamp) FROM thermostat_history th2 WHERE th2.id = :selectId)")
    List<ThermostatHistory> fetchLast12hForId(@Param("selectId") Short selectId);

    @Query("SELECT th.* FROM thermostat_history th " +
            "INNER JOIN ( " +
            "    SELECT id, MAX(timestamp) AS max_timestamp " +
            "    FROM thermostat_history " +
            "    GROUP BY id " +
            ") AS latest " +
            "ON th.id = latest.id AND th.timestamp = latest.max_timestamp")
    List<ThermostatHistory> findLatestHistoryForEachId();
}
