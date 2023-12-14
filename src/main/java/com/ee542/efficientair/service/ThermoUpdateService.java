package com.ee542.efficientair.service;

import com.ee542.efficientair.database.UpdateRepository;
import com.ee542.efficientair.entity.ThermostatHistory;
import com.ee542.efficientair.entity.ThermostatUpdate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThermoUpdateService {
    private final UpdateRepository updateRepository;
    private final ThermoHistService thermoHistService;
    private final JdbcTemplate jdbcTemplate;

    public ThermoUpdateService(UpdateRepository updateRepository,
                               ThermoHistService thermoHistService,
                               JdbcTemplate jdbcTemplate){
        this.updateRepository = updateRepository;
        this.thermoHistService = thermoHistService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ThermostatUpdate> findAll(){
        return updateRepository.findAllItems();
    }

    public List<ThermostatUpdate> getCurrent(){
        return updateRepository.findLatestUpdatesForEachId();
    }

    public ThermostatUpdate getCurrentById(Short id) {
        List<ThermostatUpdate> listOfUpdates = updateRepository.getById(id);

        // Sort the updates based on timestamp in descending order
        listOfUpdates.sort((update1, update2) -> Long.compare(update2.timestamp(), update1.timestamp()));

        // Check if the list is not empty and return the first element (latest update)
        if (!listOfUpdates.isEmpty()) {
            return listOfUpdates.get(0);
        }
        return null;
    }

    // boolean cooling: true for cooling, false for heating
    // this function can increment or decrement current temperature setting based on summer/winter:
    // if it's in summer, this function is called on a schedule to increment temperature setting
    // opposite in winter
    // higher temperature in summer and lower in winter, can decrease energy usage
    public List<ThermostatUpdate> increment(List<ThermostatUpdate> latestUpdates, boolean cooling) {
        return latestUpdates.stream()
                .map(latestUpdate -> {
                    // Check the backoffFlag before updating
                    if (latestUpdate.backoffFlag() == 1) {
                        return latestUpdate; // Return the element unchanged
                    } else {
                        // Update the temperature
                        return new ThermostatUpdate(
                                latestUpdate.id(),
                                Instant.now().getEpochSecond(),
                                (short) (cooling ? latestUpdate.temperature() + 1 : latestUpdate.temperature() - 1),
                                latestUpdate.backoffFlag());
                    }
                })
                .collect(Collectors.toList());
    }

    public ThermostatUpdate backoff(List<ThermostatHistory> histories, ThermostatUpdate update){
        int n = histories.size();
        short id = update.id();
        short temperature = update.temperature();
        Long timestamp = Instant.now().getEpochSecond();
        short backoffFlag = update.backoffFlag();
        boolean backoffIndicator = false;
        int backoffValue = 0;

        for(int i=0; i<n-20; i++){
            int j = i+20;
            ThermostatHistory a = histories.get(i);
            ThermostatHistory b = histories.get(j);
            if(a.temperature() - b.temperature() >= 5){
                backoffFlag = 1;
                backoffIndicator = true;
                backoffValue = a.temperature() - b.temperature();
                break;
            }
        }

        if(backoffIndicator) {
            if (backoffValue > 0) {
                // temperature rapidly decreased, indicating cooling
                return new ThermostatUpdate(id, timestamp, (short) (temperature - 2), backoffFlag);
            } else {
                return new ThermostatUpdate(id, timestamp, (short) (temperature + 2), backoffFlag);
            }
        } else {
            return new ThermostatUpdate(id, timestamp, temperature, backoffFlag);
        }
    }

    public boolean totalUpdate(boolean cooling){
        List<ThermostatUpdate> latest = this.getCurrent();

        for(int i=0; i<latest.size(); i++){
            short id = latest.get(i).id();
            ThermostatUpdate update = latest.get(i);
            List<ThermostatHistory> histories = thermoHistService.getHistoryForLast12hById(id);
            latest.set(i, this.backoff(histories, update));
        }

        List<ThermostatUpdate> latestAfterUpdate = this.increment(latest, cooling);
        insertUpdate(latestAfterUpdate);
        return true;
    }

    public void insertUpdate(List<ThermostatUpdate> updates){
        String sql = "INSERT INTO thermostat_update (id, timestamp, temperature, backoff_flag) VALUES (?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ThermostatUpdate thermostatUpdate = updates.get(i);
                ps.setShort(1, thermostatUpdate.id());
                ps.setLong(2, thermostatUpdate.timestamp());
                ps.setShort(3, thermostatUpdate.temperature());
                ps.setShort(4, thermostatUpdate.backoffFlag());
            }
            @Override
            public int getBatchSize() {
                return updates.size();
            }
        });
    }
}
