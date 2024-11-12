package com.dsi301.mealmasterserver.repositories;

import com.dsi301.mealmasterserver.entities.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InstructionRepository extends JpaRepository<Instruction, UUID> {
}
