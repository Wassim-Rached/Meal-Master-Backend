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
    private Integer step_number;
    private String text;
    private Integer time_estimate;

    @Override
    public Instruction toEntity(Recipe recipe) {
        if (step_number == null)
            throw new InputValidationException("Step number is required");
        if (text == null || text.isBlank())
            throw new InputValidationException("Text is required");
        if (time_estimate == null)
            throw new InputValidationException("Time estimate is required");

        return Instruction.builder()
                .step_number(step_number)
                .text(text)
                .time_estimate(time_estimate)
                .recipe(recipe)
                .build();
    }
}
