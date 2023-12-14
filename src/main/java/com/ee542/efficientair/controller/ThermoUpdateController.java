package com.ee542.efficientair.controller;

import com.ee542.efficientair.entity.ThermostatUpdate;
import com.ee542.efficientair.service.ThermoUpdateService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://192.168.1.183:3000", "http://localhost:3000"})
@RestController
public class ThermoUpdateController {
    private ThermoUpdateService thermoUpdateService;

    public ThermoUpdateController(ThermoUpdateService thermoUpdateService){
        this.thermoUpdateService = thermoUpdateService;
    }

    @GetMapping("/allupdates")
    public List<ThermostatUpdate> findAll(){
        return thermoUpdateService.findAll();
    }

    @GetMapping("/getcurrent")
    public List<ThermostatUpdate> getCurrent(){
        return thermoUpdateService.getCurrent();
    }

    @GetMapping("/update")
    public String updateAll(){
        if(thermoUpdateService.totalUpdate(true)) {
            return "update complete";
        } else {
            return "update error";
        }
    }

    // controlling A/C: trivial, only as a form for now
    @GetMapping("/highertemp")
    public String addTemp(){
        return "temperature add";
    }

    @GetMapping("/lowertemp")
    public String lowerTemp(){
        return "temperature decrease";
    }
}
