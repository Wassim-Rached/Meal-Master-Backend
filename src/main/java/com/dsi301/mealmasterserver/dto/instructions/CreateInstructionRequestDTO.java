package com.dsi301.mealmasterserver.dto.instructions;

import com.dsi301.mealmasterserver.entities.Instruction;
import com.dsi301.mealmasterserver.entities.Recipe;
import com.dsi301.mealmasterserver.exceptions.InputValidationException;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInstructionRequestDTO implements ToEntity<Instruction, Recipe> {
    private Integer stepNumber;
    private String text;
    private Integer timeEstimate;

    @Override
    public Instruction toEntity(Recipe recipe) {
        if (stepNumber == null)
            throw new InputValidationException("Step number is required");
        if (text == null || text.isBlank())
            throw new InputValidationException("Text is required");
        if (timeEstimate == null)
            throw new InputValidationException("Time estimate is required");

        return Instruction.builder()
                .stepNumber(stepNumber)
                .text(text)
                .timeEstimate(timeEstimate)
                .recipe(recipe)
                .build();
    }

    public static CreateInstructionRequestDTO fake() {
        CreateInstructionRequestDTO dto = new CreateInstructionRequestDTO();
        dto.setStepNumber(1);
        dto.setText("Fake instruction");
        dto.setTimeEstimate(10);
        return dto;
    }
}
