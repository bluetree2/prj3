package com.example.backend.board.repository;

import com.example.backend.Board;
import com.example.backend.board.dto.BoardListInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<BoardListInfo> findAllByOrderByIdDesc();
    //projection : 필요한 정보만 뽑아내느 것
    
}