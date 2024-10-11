package com.dsi301.mealmasterserver.dto.instructions;

import com.dsi301.mealmasterserver.entities.Instruction;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GeneralInstructionDTO {
    private UUID id;
    private Integer step_number;
    private String text;
    private Integer time_estimate;

    public GeneralInstructionDTO(Instruction instruction) {
        this.id = instruction.getId();
        this.step_number = instruction.getStep_number();
        this.text = instruction.getText();
        this.time_estimate = instruction.getTime_estimate();
    }
}
