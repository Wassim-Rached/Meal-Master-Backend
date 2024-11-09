package com.dsi301.mealmasterserver.dto;

import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateAccountRequestDTO implements ToEntity<Account,Void> {
    private String username;
    private String password;

    @Override
    public Account toEntity(Void v) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);

        return account;
    }
}
