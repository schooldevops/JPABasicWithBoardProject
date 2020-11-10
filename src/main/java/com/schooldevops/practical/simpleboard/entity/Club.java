package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.constants.ClubLevel;
import com.schooldevops.practical.simpleboard.dto.ClubDto;
import com.schooldevops.practical.simpleboard.entity.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ClubLevel clubLevel;

    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "club")
    private Set<ClubUsers> clubUsers;

    @Transient
    public ClubDto getDTO() {
        return ClubDto.builder()
                .id(this.id)
                .name(this.name)
                .clubLevel(this.clubLevel)
                .createdAt(this.createdAt)
                .build();
    }
}
