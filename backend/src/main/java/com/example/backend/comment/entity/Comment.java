package com.example.backend.comment.entity;

import com.example.backend.Board;
import com.example.backend.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comment", schema = "prj3")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author")
    private Member author;

    private String comment;

    @Column(insertable = false, updatable = false)
    private LocalDateTime insertedAt;

}