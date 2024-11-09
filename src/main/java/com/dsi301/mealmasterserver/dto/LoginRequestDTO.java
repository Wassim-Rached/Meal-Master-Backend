package com.dsi301.mealmasterserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String username;
    private String password;
}
