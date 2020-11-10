package com.schooldevops.practical.simpleboard.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class ClubUsersKey implements Serializable {

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "user_id")
    private String userId;
}
