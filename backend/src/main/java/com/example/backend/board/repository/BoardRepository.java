package com.example.backend.board.repository;

import com.example.backend.Board;
import com.example.backend.board.dto.BoardDto;
import com.example.backend.board.dto.BoardListDto;
import com.example.backend.board.dto.BoardListInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllByOrderByIdDesc();


    @Query(value = """
            SELECT new com.example.backend.board.dto.BoardListDto(
                   b.id,
                   b.title ,
                   m.nickName,
                   b.insertedAt )
            FROM Board b join Member m
                        on b.author.email = m.email
            ORDER BY b.id DESC
            """)
    List<BoardListDto> findAllBy();

    //                               todo :
    @Query(value = """
            SELECT new com.example.backend.board.dto.BoardDto(        
                    b.id,
                   b.title,
                   b.content,
                   m.email ,
                   m.nickName ,
                   b.insertedAt)
            FROM Board b JOIN Member m
                ON b.author.email = m.email
            WHERE b.id = :id
            """)
    BoardDto findBoardById(Integer id);
    //projection : 필요한 정보만 뽑아내느 것

}