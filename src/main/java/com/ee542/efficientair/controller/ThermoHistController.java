package com.ee542.efficientair.controller;

import com.ee542.efficientair.entity.ThermostatHistory;
import com.ee542.efficientair.service.ThermoHistService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://192.168.1.183:3000", "http://localhost:3000"})
@RestController
public class ThermoHistController {
    private final ThermoHistService thermoHistService;

    public ThermoHistController(ThermoHistService thermoHistService){
        this.thermoHistService = thermoHistService;
    }

    @GetMapping("/allhistory")
    public List<ThermostatHistory> getAllHistory(){
        return thermoHistService.getAllHistory();
    }

    @GetMapping("/last12h")
    public List<ThermostatHistory> getLast12h(@RequestParam String id){
        try{
            short value = Short.parseShort(id);
            return thermoHistService.getHistoryForLast12hById(value);
        } catch (NumberFormatException e){
            return null;
        }
    }

    @GetMapping("/readings")
    public List<ThermostatHistory> getLatestReading(){
        return thermoHistService.latestReading();
    }
}
