package com.example.backend.board.service;


import com.example.backend.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.board.dto.BoardDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void add(BoardDto dto) {
        // entity에 dto의 값을 옮겨 담고
        Board board = new Board();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setAuthor(dto.getAuthor());

        //repository에 save실행
        boardRepository.save(board);
    }

    public boolean validate(BoardDto dto) {
        if (dto.getTitle() == null || dto.getTitle().trim().isBlank()) {
            return false;
        }

        return true;
    }
}
