package com.dsi301.mealmasterserver.dto.schedules;

import com.dsi301.mealmasterserver.entities.Folder;
import com.dsi301.mealmasterserver.entities.Schedule;
import com.dsi301.mealmasterserver.interfaces.dto.ToEntity;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

import java.util.UUID;

@Getter
@Setter
public class CreateScheduleRequestDTO implements ToEntity<Schedule, Void> {
    private Instant scheduledDate;
    private UUID folderId;

    @Override
    public Schedule toEntity(Void aVoid) {
        Schedule schedule = new Schedule();
        schedule.setScheduledDate(scheduledDate);

        Folder folder = new Folder();
        folder.setId(folderId);
        schedule.setFolder(folder);

        return schedule;
    }

}
