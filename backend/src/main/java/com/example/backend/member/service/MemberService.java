package com.example.backend.member.service;

import com.example.backend.member.dto.MemberDto;
import com.example.backend.member.dto.MemberListInfo;
import com.example.backend.member.entity.Member;
import com.example.backend.member.dto.MemberForm;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void add(MemberForm memberForm) {

        System.out.println("memberFrom = " + memberForm);

        if (this.validate(memberForm)) {
            Member member = new Member();
            member.setEmail(memberForm.getEmail());
            member.setPassword(memberForm.getPassword());
            member.setInfo(memberForm.getInfo());
            member.setNickName(memberForm.getNickName());

            memberRepository.save(member);
        }

    }

    private boolean validate(MemberForm memberForm) {
        //email 중복여부
        Optional<Member> db1 = memberRepository.findById(memberForm.getEmail());
        if (db1.isPresent()) {
            throw new RuntimeException("이미 가입된 이메일입니다.");
        }
        // nickname 중복여부
        Optional<Member> db2 = memberRepository.findByNickName(memberForm.getNickName());
        if (db2.isPresent()) {
            throw new RuntimeException("이미 가입된 닉네임입니다.");
        }
        // email 값 여부
        if (memberForm.getEmail() == null || memberForm.getEmail().trim().isBlank()) {
            new RuntimeException("이매일을 입력해야 합니다.");
        }
        // 형식에 맞는지
        String email = memberForm.getEmail();
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}", email)) {
            throw new RuntimeException("이메일 형식에 맞지 않습니다");
        }
        // password 값 여부
        if (memberForm.getPassword() == null || memberForm.getPassword().trim().isBlank()) {
            throw new RuntimeException("패스워드를 입력해야 합니다.");
        }
        // password 여부
        if (memberForm.getNickName() == null || memberForm.getNickName().trim().isBlank()) {
            throw new RuntimeException("별명을 입력해야 합니다");
        }
        return true;
    }

    public List<MemberListInfo> list() {
        return memberRepository.findAllBy();
    }

    public MemberDto get(String email) {
        System.out.println("email = " + email);
        Member db = memberRepository.findById(email).get();

        MemberDto member = new MemberDto();
        member.setNickName(db.getNickName());
        member.setInfo(db.getInfo());
        member.setEmail(db.getEmail());
        member.setInsertedAt(db.getInsertedAt());


        return member;


    }

    public void delete(MemberForm memberForm) {
        Member db = memberRepository.findById(memberForm.getEmail()).get();
        if (db.getPassword().equals(memberForm.getPassword())) {
            memberRepository.delete(db);
        } else {
            throw new RuntimeException("함호가 일치하지 않습니다");
        }
    }

    public void update(MemberForm memberForm) {
        // 조회
        Member db = memberRepository.findById(memberForm.getEmail()).get();

        //암호 확인
        if (!db.getPassword().equals(memberForm.getPassword())) {
            throw new RuntimeException("암호가 일치하지 않습니다.");
        }
        {

            // 변경
            db.setNickName(memberForm.getNickName());
            db.setInfo(memberForm.getInfo());

            // 저장
            memberRepository.save(db);
        }
    }
}
