package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.constants.ClubLevel;
import com.schooldevops.practical.simpleboard.entity.Club;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ClubDto {

    private Long id;

    private String name;

    private ClubLevel clubLevel;

    private LocalDateTime createdAt;

    private Set<ClubUsersDto> clubUsers;

    @JsonIgnore
    public Club getEntity() {
        return Club.builder()
                .name(this.name)
                .clubLevel(this.clubLevel == null ? ClubLevel.SEED : this.clubLevel)
                .createdAt(this.createdAt == null ? LocalDateTime.now() : this.createdAt)
                .build();
    }
}
