package com.example.backend.member.controller;

import com.example.backend.member.dto.ChangePasswordForm;
import com.example.backend.member.dto.MemberDto;
import com.example.backend.member.dto.MemberForm;
import com.example.backend.member.dto.MemberListInfo;
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
    public ResponseEntity<?> add(@RequestBody MemberForm memberForm) {
        System.out.println("memberFrom = " + memberForm);
        try {
            memberService.add(memberForm);
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

    @GetMapping("list")
    public List<MemberListInfo> list() {
        return memberService.list();
    }

    @GetMapping(params = "email")
    public MemberDto getemail(String email) {
        return memberService.get(email);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMember(@RequestBody MemberForm memberForm) {
        System.out.println("memberForm = " + memberForm);
        try {
            memberService.delete(memberForm);

        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.status(401).body(
                    Map.of("message", Map.of("type", "error", "text", message)));
        }

        return ResponseEntity.ok().body(
                Map.of("message", Map.of("type", "success", "text", "탈퇴가 완료되었습니다")));

    }

    @PutMapping()
    public ResponseEntity<?> updateMember(@RequestBody MemberForm memberForm) {
        System.out.println("memberForm = " + memberForm);

        try {
            memberService.update(memberForm);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.status(401).body(
                    Map.of("message", Map.of("type", "error", "text", message)));
        }

        return ResponseEntity.ok().body(
                Map.of("message", Map.of("type", "success", "text", "수정이 완료되었습니다")));


    }

    @PutMapping("changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm memberForm) {
        System.out.println("memberForm = " + memberForm);

        try {
            memberService.changePassword(memberForm);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.status(401).body(
                    Map.of("message", Map.of("type", "error", "text", message)));
        }

        return ResponseEntity.ok().body(
                Map.of("message", Map.of("type", "success", "text", "암호가 변경되었습니다."))
        );
    }

}
