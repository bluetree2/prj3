package com.example.backend.board.controller;

import com.example.backend.board.dto.BoardDto;
import com.example.backend.board.dto.BoardListInfo;
import com.example.backend.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@Controller
//@ResponseBody
@RestController // Controller + ResponseBody
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("{id}")
    public BoardDto getBoard(@PathVariable Integer id) {
        return boardService.getBoardById(id);
    }


    @GetMapping("list")
    public List<BoardListInfo> getAllBoards() {
        System.out.println("BoardController.getAllBoards");
        
        return boardService.list();
    }


    @PostMapping("add")
    public ResponseEntity<Object> add(@RequestBody BoardDto dto) {


        // 값들이 유효한지 확인
        boolean result = boardService.validate(dto);

        if (result) {

            boardService.add(dto);

            return ResponseEntity.ok().body(Map.of(
                    "success", Map.of(
                            "code", "success",
                            "text", "새 글이 저장되었습니다")));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", Map.of(
                            "type", "error",
                            "text", "입력한 내용이 유효하지 않습니다.")));
        }
    }
}
