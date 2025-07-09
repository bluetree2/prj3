package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("add")
    @ResponseBody
    public String add(@RequestBody BoardDto dto) {
        // service 에게 넘겨서 일 시키기
        boardService.add(dto);

        System.out.println(dto);

        return "board/add";
    }
}
