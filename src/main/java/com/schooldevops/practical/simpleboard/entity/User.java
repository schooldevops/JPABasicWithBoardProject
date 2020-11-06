package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String name;

    @Column
    private String birth;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private UserDetail userDetail;

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
        userDetail.setUser(this);
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boards;

    @Transient
    public UserDto getDTO() {
        UserDto userDTO = UserDto.builder()
                .id(this.id)
                .name(this.name)
                .birth(this.birth)
                .createdAt(this.createdAt)
                .build();

        if (userDetail != null) {
            userDTO.setUserDetail(userDetail.getDTO());
        }
        return userDTO;
    }
}
