package com.example.backend.member.service;

import com.example.backend.member.entity.Member;
import com.example.backend.member.dto.MemberFrom;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberFrom memberFrom) {
        if (this.validate(memberFrom)) {

            Member member = new Member();
            member.setEmail(member.getEmail());
            member.setPassword(member.getPassword());
            member.setInfo(member.getInfo());
            member.setNickName(member.getNickName());

            memberRepository.save(member);
        }

    }

    private boolean validate(MemberFrom memberFrom) {
        //email 중복여부
        Optional<Member> db1 = memberRepository.findById(memberFrom.getEmail());
        if (db1.isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }
        // nickname 중복여부
        Optional<Member> db2 = memberRepository.findByNickName(memberFrom.getNickName());
        if (db2.isPresent()) {
            throw new RuntimeException("이미 가입된 닉네임입니다.");
        }
        // email 값 여부
        if (memberFrom.getEmail() == null || memberFrom.getEmail().trim().isBlank()) {
            new RuntimeException("이매일을 입력해야 합니다.");
        }
        // 형식에 맞는지
        String email = memberFrom.getEmail();
        if (!Pattern.matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}", email)) {
            throw new RuntimeException("이메일 형식에 맞지 않습니다");
        }
        // password 값 여부
        if (memberFrom.getPassword() == null || memberFrom.getPassword().trim().isBlank()) {
            throw new RuntimeException("패스워드를 입력해야 합니다.");
        }
        // password 여부
        if (memberFrom.getNickName() == null || memberFrom.getNickName().trim().isBlank()) {
            throw new RuntimeException("");
        }
        return false;
    }
}
