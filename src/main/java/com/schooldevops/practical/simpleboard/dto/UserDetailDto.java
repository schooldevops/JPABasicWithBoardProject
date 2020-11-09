package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.constants.Role;
import com.schooldevops.practical.simpleboard.entity.UserDetail;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserDetailDto {

    private String id;

    private String nick;

    private String avatarImg;

    private String category;

//    private Role role;

    private LocalDateTime joinedAt;

    private LocalDateTime modifiedAt;

    @JsonIgnore
    public UserDetail getEntity() {
        LocalDateTime now = LocalDateTime.now();
        UserDetail userDetail = UserDetail.builder()
                .id(this.id)
                .nick(this.nick)
                .avatarImg(this.avatarImg)
                .category(this.category)
//                .role(this.role)
                .joinedAt(now)
                .modifiedAt(now)
                .build();

        return userDetail;
    }
}
