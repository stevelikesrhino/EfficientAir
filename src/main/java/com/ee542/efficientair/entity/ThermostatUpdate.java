package com.ee542.efficientair.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("thermostat_update")
public record ThermostatUpdate(
        @Id
        Short id,
        Long timestamp,
        Short temperature,

        @JsonProperty("backoff_flag")
        Short backoffFlag
){}