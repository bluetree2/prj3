package com.example.backend.comment.repository;

import com.example.backend.Board;
import com.example.backend.comment.dto.CommentListDto;
import com.example.backend.comment.entity.Comment;
import com.example.backend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("""
            SELECT new com.example.backend.comment.dto.CommentListDto(
                c.id,
                c.board.id,
                c.author.email,
                c.author.nickName,
                c.comment,
                c.insertedAt
                )
            FROM Comment c Join c.author m
            where c.board.id = :boardId
            ORDER BY c.id   desc 
            """)
    List<CommentListDto> listByBoardId(Integer boardId);

    void deleteByBoardId(Integer id);

    void deleteByAuthor(Member db);

    void deleteByBoard(Board board);
}