package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RoleDto {

    private Long id;
    private String name;
    private List<UserDto> users;

    @JsonIgnore
    public RoleEntity getEntity() {
        return RoleEntity.builder()
                .name(this.name)
                .build();
    }
}
