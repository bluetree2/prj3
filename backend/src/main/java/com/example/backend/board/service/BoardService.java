package com.example.backend.board.service;


import com.example.backend.Board;
import com.example.backend.board.dto.BoardListDto;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.board.dto.BoardDto;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public Map<String, Object> list(String keyword, Integer pageNumber) {
//        return boardRepository.findAllByOrderByIdDesc();
//        return boardRepository.findAllBy(keyword, PageRequest.of(-1, 10));
        Page<BoardListDto> boardListDtoPage
                = boardRepository.findAllBy(keyword, PageRequest.of(pageNumber - 1, 10));

        int totalPages = boardListDtoPage.getTotalPages();
        int rightPageNumber = ((pageNumber - 1) / 10 + 1) * 10;
        int leftPageNumber = rightPageNumber - 9;
        rightPageNumber = Math.min(rightPageNumber, totalPages);
        leftPageNumber = Math.max(leftPageNumber, 1);

        var pageInfo = Map.of("totalPages", totalPages,
                "rightPageNumber", rightPageNumber,
                "leftPageNumber", leftPageNumber,
                "currentPageNumber", pageNumber);
//        todo :


        System.out.println("pageinfo = " + pageInfo);
        System.out.println("boardListDtoPage = " + boardListDtoPage.getContent());
        return Map.of("pageInfo", pageInfo,
                "boardList", boardListDtoPage.getContent());

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

        if (db.getAuthor().getEmail().equals(authentication.getName())) {
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
