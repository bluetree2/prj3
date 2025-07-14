package com.example.backend.board.service;


import com.example.backend.Board;
import com.example.backend.board.dto.BoardListDto;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.board.dto.BoardDto;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Member author = memberRepository.findById(authentication.getName()).get();
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

    public List<BoardListDto> list() {
//        return boardRepository.findAllByOrderByIdDesc();
        return boardRepository.findAllBy();
    }

    public BoardDto getBoardById(Integer id) {
        BoardDto board = boardRepository.findBoardById(id);
//        BoardDto dto = new BoardDto();
//        dto.setId(board.getId());
//        dto.setTitle(board.getTitle());
//        dto.setContent(board.getContent());
//        dto.setAuthor(board.getAuthor());
//        dto.setInsertedAt(board.getInsertedAt());

        return board;
    }


    public void deleteById(Integer id, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다");
        }
        Board db = boardRepository.findById(id).get();
        if (!db.getAuthor().getEmail().equals(authentication.getName())) {
            boardRepository.deleteById(id);
        } else {
            throw new RuntimeException("권한이 없습니다");
        }
    }

    public void update(BoardDto dto, Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("권한이 없습니다.");
        }

        //조회  
        Board board = boardRepository.findById(dto.getId()).get();

        if (board.getAuthor().getEmail().equals(authentication.getName())) {
            // 변경
            board.setTitle(dto.getTitle());
            board.setContent(dto.getContent());
            // 저장
            boardRepository.save(board);

        } else {
            throw new RuntimeException("권한이 없습니다.");
        }

    }
}
