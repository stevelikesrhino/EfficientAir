package com.ee542.efficientair.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("thermostat_history")
public record ThermostatHistory (
    @Id
    Short id,
    Long timestamp,
    Short temperature,
    Short humidity
){}

