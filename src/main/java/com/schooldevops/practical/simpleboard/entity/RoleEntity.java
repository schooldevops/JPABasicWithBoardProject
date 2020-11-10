package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.dto.RoleDto;
import com.schooldevops.practical.simpleboard.entity.listener.RoleEventTrailListener;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EntityListeners(RoleEventTrailListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    public void addUser(User user) {
        if (users == null) {
            users = new HashSet<>();
        }

        users.add(user);
    }

    @Transient
    public RoleDto getDTO() {

        RoleDto roleDto = RoleDto.builder()
                .id(this.id)
                .name(this.name)
                .build();

        System.out.println("Role DTO: " + roleDto);

        return roleDto;
    }
}
