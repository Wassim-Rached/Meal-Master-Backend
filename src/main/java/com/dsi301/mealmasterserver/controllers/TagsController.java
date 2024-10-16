package com.dsi301.mealmasterserver.controllers;

import com.dsi301.mealmasterserver.dto.tags.GeneralTagDTO;
import com.dsi301.mealmasterserver.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagsController {
    private final TagRepository tagRepository;

    @GetMapping("/search")
    public Iterable<GeneralTagDTO> searchTags(@RequestParam(defaultValue = "") String name) {
        return GeneralTagDTO.fromEntities(tagRepository.findAllByNameContaining(name));
    }
}
