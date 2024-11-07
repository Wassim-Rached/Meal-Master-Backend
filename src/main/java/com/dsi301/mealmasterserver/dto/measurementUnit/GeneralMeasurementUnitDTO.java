package com.dsi301.mealmasterserver.dto.measurementUnit;

import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.util.stream.StreamSupport;

@Getter
@Setter
public class GeneralMeasurementUnitDTO {
    private UUID id;
    private String name;

    public GeneralMeasurementUnitDTO(MeasurementUnit measurementUnit) {
        this.id = measurementUnit.getId();
        this.name = measurementUnit.getName();
    }

    public static Iterable<GeneralMeasurementUnitDTO> fromEntities(Iterable<MeasurementUnit> all) {
        return StreamSupport.stream(all.spliterator(), false)
                .map(GeneralMeasurementUnitDTO::new)
                .toList();
    }
}
