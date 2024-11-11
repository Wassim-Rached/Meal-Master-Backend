package com.dsi301.mealmasterserver.dto.instructions;

import com.dsi301.mealmasterserver.entities.Instruction;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GeneralInstructionDTO {
    private UUID id;
    private Integer stepNumber;
    private String text;
    private Integer timeEstimate;

    public GeneralInstructionDTO(Instruction instruction) {
        this.id = instruction.getId();
        this.stepNumber = instruction.getStepNumber();
        this.text = instruction.getText();
        this.timeEstimate = instruction.getTimeEstimate();
    }
}
