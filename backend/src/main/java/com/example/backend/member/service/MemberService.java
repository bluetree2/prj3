package com.example.backend.member.service;

import com.example.backend.Board;
import com.example.backend.board.repository.BoardRepository;
import com.example.backend.comment.repository.CommentRepository;
import com.example.backend.member.dto.*;
import com.example.backend.member.entity.Auth;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.AuthRepository;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthRepository authRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void add(MemberForm memberForm) {

        System.out.println("memberFrom = " + memberForm);

        if (this.validate(memberForm)) {
            Member member = new Member();
            member.setEmail(memberForm.getEmail());
//            member.setPassword(memberForm.getPassword()); // 암호화
            member.setPassword(passwordEncoder.encode(memberForm.getPassword()));

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
        if (!Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email)) {
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
//        if (db.getPassword().equals(memberForm.getPassword())) {
        // matches(평문,인코딩문)
        if (passwordEncoder.matches(memberForm.getPassword(), db.getPassword())) {
            // 회원이 쓴 댓글 지우기
            commentRepository.deleteByAuthor(db);
            // 회원이 쓴 게시물에 달린 댓글 지우기
            // 회원이 쓴 게시물 얻고
            List<Board> byAuthor = boardRepository.findByAuthor(db);
            // 그 게시물로 댓글 지우기
            for (Board board : byAuthor) {
                commentRepository.deleteByBoard(board);
            }

            // 회원이 쓴 게시물 지우기
            boardRepository.deleteByAuthor(db);
            // 회원 정보 지우기
            memberRepository.delete(db);
        } else {
            throw new RuntimeException("암호가 일치하지 않습니다");
        }
    }

    public void update(MemberForm memberForm) {
        // 조회
        Member db = memberRepository.findById(memberForm.getEmail()).get();

        //암호 확인
//        if (!db.getPassword().equals(memberForm.getPassword())) {
        if (passwordEncoder.matches(memberForm.getPassword(), db.getPassword()) == false) {
            throw new RuntimeException("암호가 일치하지 않습니다.");
        } else {

            // 변경
            db.setNickName(memberForm.getNickName());
            db.setInfo(memberForm.getInfo());

            // 저장
            memberRepository.save(db);
        }
    }

    public void changePassword(ChangePasswordForm data) {
        Member db = memberRepository.findById(data.getEmail()).get();

//        if (db.getPassword().equals(data.getOldPassword())) {
        if (passwordEncoder.matches(data.getOldPassword(), db.getPassword())) {
//            db.setPassword(data.getNewPassword());
            db.setPassword(passwordEncoder.encode(data.getNewPassword()));
            memberRepository.save(db);
        } else {
            throw new RuntimeException("이전 패스워드가 일치하지 않습니다");
        }

    }

    public String getToken(MemberLoginForm loginForm) {
        // 해당 email의 데이터 있느지
        Optional<Member> db = memberRepository.findById(loginForm.getEmail());
        if (db.isPresent()) {
            // 있으면 패스워드 맞는지
//            if (db.get().getPassword().equals(loginForm.getPassword())) {
            if (passwordEncoder.matches(loginForm.getPassword(), db.get().getPassword())) {
                List<Auth> authList = authRepository.findByMember(db.get());

                String authListString = authList.stream()
                        .map(auth -> auth.getId().getAuthName())
                        .collect(Collectors.joining(" "));

                // token 만들어서 리턴
                JwtClaimsSet claims = JwtClaimsSet.builder()
                        .subject(loginForm.getEmail())
                        .issuer("self")
                        .issuedAt(Instant.now())
                        .expiresAt(Instant.now().plusSeconds(60 * 60 * 24 * 7))
                        .claim("scp", authListString)
                        .build();

                return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            }

        }

        throw new RuntimeException("이메일 또는 패스워드가 일치하지 않습니다.");
        // token 만들어서 리턴
    }
}
