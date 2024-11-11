package com.dsi301.mealmasterserver.dto.accounts;

import com.dsi301.mealmasterserver.entities.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeneralAccountDTO {
    private String username;
    private String avatarUrl;

    public GeneralAccountDTO(Account account) {
        this.username = account.getUsername();
        this.avatarUrl = account.getAvatarUrl();
    }
}
