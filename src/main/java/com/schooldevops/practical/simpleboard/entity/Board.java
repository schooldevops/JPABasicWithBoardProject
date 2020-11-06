package com.schooldevops.practical.simpleboard.entity;

import com.schooldevops.practical.simpleboard.constants.BoardCategory;
import com.schooldevops.practical.simpleboard.constants.ContentStatus;
import com.schooldevops.practical.simpleboard.dto.BoardDto;
import com.schooldevops.practical.simpleboard.entity.converter.BooleanYNConverter;
import com.schooldevops.practical.simpleboard.entity.converter.LocalDateTimeConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardCategory category;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_Board_to_user"))
    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Integer readCount = new Integer(0);

    @Column(nullable = false)
    private Integer goodCount = new Integer(0);

    @Column(nullable = false)
    private Integer badCount = new Integer(0);

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentStatus status;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;

    @Transient
    public BoardDto getDTO() {
        return BoardDto.builder()
                .id(this.id)
                .category(this.category)
                .title(this.title)
                .contents(this.contents)
                .user(this.user.getDTO())
                .readCount(this.readCount)
                .goodCount(this.goodCount)
                .badCount(this.badCount)
                .status(this.status)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }
}
