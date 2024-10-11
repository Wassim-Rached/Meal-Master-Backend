package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.measurementUnit.GeneralMeasurementUnitDTO;
import com.dsi301.mealmasterserver.repositories.MeasurementUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/measurements-units")
@RequiredArgsConstructor
public class MeasurementsUnitsController {

    private final MeasurementUnitRepository measurementUnitRepository;

    @GetMapping
    public Iterable<GeneralMeasurementUnitDTO> getMeasurements() {
        return GeneralMeasurementUnitDTO.fromEntities(measurementUnitRepository.findAll());
    }
}
