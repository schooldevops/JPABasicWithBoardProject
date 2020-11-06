package com.schooldevops.practical.simpleboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schooldevops.practical.simpleboard.constants.BoardCategory;
import com.schooldevops.practical.simpleboard.constants.ContentStatus;
import com.schooldevops.practical.simpleboard.entity.Board;
import com.schooldevops.practical.simpleboard.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BoardDto {

    private Long id;

    private BoardCategory category;

    private String title;

    private String contents;

    private UserDto user;

    private Integer readCount;

    private Integer goodCount;

    private Integer badCount;

    private ContentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @JsonIgnore
    public Board getEntity() {
        return Board.builder()
                .category(this.category)
                .title(this.title)
                .contents(this.contents)
                .status(this.status)
                .build();
    }
}
