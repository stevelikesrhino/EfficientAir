package com.ee542.efficientair.service;

import com.ee542.efficientair.database.ThermostatHistoryRepository;
import com.ee542.efficientair.entity.ThermostatHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThermoHistService {
    private final ThermostatHistoryRepository thermostatHistoryRepository;
    private final JdbcTemplate jdbcTemplate;

    public ThermoHistService(ThermostatHistoryRepository thermostatHistoryRepository,
                             JdbcTemplate jdbcTemplate){
        this.thermostatHistoryRepository = thermostatHistoryRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ThermostatHistory> getAllHistory(){
        return thermostatHistoryRepository.findAllItems();
    }

    public List<ThermostatHistory> getHistoryById(Short id){
        return thermostatHistoryRepository.getById(id);
    }

    public List<ThermostatHistory> getHistoryByTime(Long timestamp){
        return thermostatHistoryRepository.getByTime(timestamp);
    }

    public List<ThermostatHistory> getHistoryByIdTime(Short id, Long timestamp){
        return thermostatHistoryRepository.getByIdTime(id, timestamp);
    }

    public List<ThermostatHistory> getHistoryForLast12hById(Short id){
        return thermostatHistoryRepository.fetchLast12hForId(id);
    }

    public List<ThermostatHistory> latestReading(){
        return thermostatHistoryRepository.findLatestHistoryForEachId();
    }

}
