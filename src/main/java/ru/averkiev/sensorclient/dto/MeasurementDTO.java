package ru.averkiev.sensorclient.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    private Double value;
    private Boolean isRaining;
    private SensorDTO sensor;
}
