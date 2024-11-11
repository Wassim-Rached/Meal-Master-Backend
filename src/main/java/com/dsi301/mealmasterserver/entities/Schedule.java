package com.dsi301.mealmasterserver.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, name = "scheduled_date")
    private Instant scheduledDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "folder_id",nullable = false)
    private Folder folder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", scheduledDate=" + scheduledDate +
                ", folder=" + folder +
                ", account=" + account +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule schedule)) return false;
        return getId().equals(schedule.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
