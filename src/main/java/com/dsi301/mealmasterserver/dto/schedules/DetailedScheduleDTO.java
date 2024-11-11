package com.dsi301.mealmasterserver.dto.schedules;

import com.dsi301.mealmasterserver.dto.accounts.GeneralAccountDTO;
import com.dsi301.mealmasterserver.dto.folders.DetailedFolderDTO;
import com.dsi301.mealmasterserver.entities.Schedule;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;


@Getter
@Setter
public class DetailedScheduleDTO {
    private UUID id;
    private Instant scheduledDate;
    private DetailedFolderDTO folder;
    private GeneralAccountDTO account;

    public DetailedScheduleDTO(Schedule schedule) {
        this.id = schedule.getId();
        this.scheduledDate = schedule.getScheduledDate();
        this.folder = new DetailedFolderDTO(schedule.getFolder());
        this.account = new GeneralAccountDTO(schedule.getAccount());
    }

    public static Iterable<DetailedScheduleDTO> fromEntities(Collection<Schedule> schedules) {
        return schedules.stream().map(DetailedScheduleDTO::new).toList();
    }
}
