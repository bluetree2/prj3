package com.example.backend.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentListDto {
    private Integer id;
    private Integer boardId;
    private String authorNickName;
    private String comment;
    private LocalDateTime insertedAt;
}
