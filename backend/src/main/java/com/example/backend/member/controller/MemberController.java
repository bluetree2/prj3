package com.example.backend.member.controller;

import com.example.backend.member.dto.MemberFrom;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody MemberFrom memberFrom) {
        System.out.println("memberFrom = " + memberFrom);
        try {
            memberService.add(memberFrom);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.badRequest().body(
                    Map.of("message", Map.of("type", "error", "text", message)));
        }

        return ResponseEntity.ok().body(
                Map.of("message", Map.of(
                        "type", "success",
                        "text", "회원 가입 되었습니다."
                ))
        );
    }

}
