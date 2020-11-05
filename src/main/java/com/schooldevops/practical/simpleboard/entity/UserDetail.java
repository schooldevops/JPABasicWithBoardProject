package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.constants.Role;
import com.schooldevops.practical.simpleboard.dto.UserDetailDto;
import com.schooldevops.practical.simpleboard.entity.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "UserDetail")
public class UserDetail {

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column
    private String nick;

    @Column
    private String avatarImg;

    @Column
    private String category;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime joinedAt;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;

    @JoinColumn(name = "id", foreignKey = @ForeignKey(name = "FK_UserDetail_to_user"))
    @OneToOne
    @MapsId
    private User user;

    @Transient
    public UserDetailDto getDTO() {
        return UserDetailDto.builder()
                .id(this.id)
                .nick(this.nick)
                .avatarImg(this.avatarImg)
                .category(this.category)
                .role(this.role)
                .joinedAt(this.joinedAt)
                .modifiedAt(this.modifiedAt)
                .build();

    }
}
