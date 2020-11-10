package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.constants.ClubUserLevel;
import com.schooldevops.practical.simpleboard.constants.ClubUserStatus;
import com.schooldevops.practical.simpleboard.dto.ClubUsersDto;
import com.schooldevops.practical.simpleboard.entity.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class ClubUsers {

    @Id
    private ClubUsersKey id;

    @ManyToOne
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClubUserLevel clubUserLevel;

    @Column(nullable = false)
    private Integer score;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ClubUserStatus clubUserStatus;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;

    public ClubUsersDto getDTO() {
        return ClubUsersDto.builder()
                .id(this.id)
                .club(this.club != null ? this.club.getDTO() : null)
                .user(this.user != null ? this.user.getDTO() : null)
                .clubUserLevel(this.clubUserLevel)
                .score(this.score)
                .clubUserStatus(this.clubUserStatus)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
