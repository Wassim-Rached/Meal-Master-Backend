package com.dsi301.mealmasterserver.dto.folders;

import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.entities.Folder;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFolderDTO implements ToEntity<Folder, Account> {
    private String name;

    @Override
    public Folder toEntity(Account account) {
        Folder folder = new Folder();
        folder.setName(name);
        folder.setAccount(account);
        return folder;
    }
}
