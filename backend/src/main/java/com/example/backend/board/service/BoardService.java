package com.example.backend.board.service;


import com.example.backend.Board;
import com.example.backend.board.dto.BoardListInfo;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.board.dto.BoardDto;
import com.example.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void add(BoardDto dto, Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // entity에 dto의 값을 옮겨 담고
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        memberRepository.findById(authentication.getName()).get();
        board.setAuthor(author);
        //todo : 수정

//        board.setAuthor(authentication.getName());

        //repository에 save실행
        boardRepository.save(board);
    }

    public boolean validate(BoardDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isBlank()) {
            System.out.println(dto.getTitle());
            return false;
        }

        if (dto.getContent() == null || dto.getContent().trim().isBlank()) {
            System.out.println(dto.getContent());
            return false;
        }


//        System.out.println("success");
        return true;
    }

    public List<BoardListInfo> list() {
        return boardRepository.findAllByOrderByIdDesc();
    }

    public BoardDto getBoardById(Integer id) {
        Board board = boardRepository.findById(id).get();
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setTitle(board.getTitle());
        dto.setContent(board.getContent());
        dto.setAuthor(board.getAuthor());
        dto.setInsertedAt(board.getInsertedAt());

        return dto;
    }


    public void deleteById(Integer id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDto dto) {
        //조회  
        Board board = boardRepository.findById(dto.getId()).get();

        // 변경
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setAuthor(dto.getAuthor());

        // 저장
        boardRepository.save(board);


    }
}
