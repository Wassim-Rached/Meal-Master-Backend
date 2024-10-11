package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.MeasurementUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MeasurementUnitRepository extends JpaRepository<MeasurementUnit, UUID> {
    Optional<MeasurementUnit> findByName(String name);
}
