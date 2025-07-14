package com.example.backend.member.controller;

import com.example.backend.member.dto.*;
import com.example.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody MemberLoginForm LoginForm) {
        System.out.println("memberForm = " + LoginForm);

        try {
            String token = memberService.getToken(LoginForm);
            return ResponseEntity.ok().body(
                    Map.of("token", token,
                            "message", Map.of(
                                    "type", "success",
                                    "text", "로그인 되었습니다."
                            ))
            );
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseEntity.status(401).body(
                    Map.of("message", Map.of("type", "error", "text", message)));
        }


    }

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
    @PreAuthorize("isAuthenticated() or hasAuthority('SCOPE_admin')")
    public ResponseEntity<?> getMember(String email, Authentication authentication) {
        if (authentication.getName().equals(email)) {
            // todo : 확인
            authentication.getAuthorities().forEach(System.out::println);
            return ResponseEntity.ok().body(memberService.get(email));
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping()
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteMember(@RequestBody MemberForm memberForm,
                                          Authentication authentication) {
        if (!authentication.getName().equals(memberForm.getEmail())) {
            return ResponseEntity.status(403).build();
        }

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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordForm memberForm,
                                            Authentication authentication) {
        if (!authentication.getName().equals(memberForm.getEmail())) {
            return ResponseEntity.status(403).build();
        }
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
