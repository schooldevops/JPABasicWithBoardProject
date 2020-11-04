package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDto {

    private String id;
    private String name;
    private String birth;
    private LocalDateTime createdAt;

    private UserDetailDto userDetail;

    @JsonIgnore
    public User getEntity() {
        User userEntity = User.builder()
                .id(this.id)
                .name(this.name)
                .birth(this.birth)
                .createdAt(LocalDateTime.now())
                .build();

        if (userDetail != null) {
            userEntity.setUserDetail(userDetail.getEntity());
        }

        return userEntity;
    }
}
