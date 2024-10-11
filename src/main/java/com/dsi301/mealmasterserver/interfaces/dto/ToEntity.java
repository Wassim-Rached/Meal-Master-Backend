package com.dsi301.mealmasterserver.interfaces.dto;

public interface ToEntity<T,k> {
    T toEntity(k additionalData);
}
