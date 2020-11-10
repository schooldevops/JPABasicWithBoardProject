package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.constants.ClubUserLevel;
import com.schooldevops.practical.simpleboard.constants.ClubUserStatus;
import com.schooldevops.practical.simpleboard.entity.ClubUsers;
import com.schooldevops.practical.simpleboard.entity.ClubUsersKey;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ClubUsersDto {

    private ClubUsersKey id;

    private ClubDto club;

    private UserDto user;

    private ClubUserLevel clubUserLevel;

    private Integer score;

    private ClubUserStatus clubUserStatus;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @JsonIgnore
    public ClubUsers getEntity() {
        return ClubUsers.builder()
                .clubUserLevel(this.clubUserLevel == null ? ClubUserLevel.BABY : this.clubUserLevel)
                .score(this.score == null ? 0 : this.score)
                .clubUserStatus(this.clubUserStatus == null ? ClubUserStatus.OPEN : this.clubUserStatus)
                .createdAt(this.createdAt == null ? LocalDateTime.now() : this.createdAt)
                .modifiedAt(null)
                .build();
    }
}
