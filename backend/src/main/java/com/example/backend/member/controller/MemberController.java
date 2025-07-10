package com.example.backend.member.controller;

import com.example.backend.member.dto.MemberFrom;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("add")
    public String add(@RequestBody MemberFrom memberFrom) {
        memberService.add(memberFrom);

        return null;
    }

}
