package com.example.backend.like.service;

import com.example.backend.Board;
import com.example.backend.like.dto.BoardLikeDto;
import com.example.backend.like.dto.LikeForm;
import com.example.backend.like.entity.BoardLike;
import com.example.backend.like.entity.BoardLikeId;
import com.example.backend.like.repository.BoardLikeRepository;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import com.example.backend.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final BoardLikeRepository boardLikeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public void update(LikeForm likeForm, Authentication authentication) {
        // 로그인 안했으면 exception
        if (authentication == null) {
            throw new RuntimeException("로그인 하세요");
        }

        // 게시물 번호와 로그인이메일로 like 데이터 얻어서
        var boardLike = boardLikeRepository.findByBoardIdAndMemberEmail(
                likeForm.getBoardId(), authentication.getName());

        System.out.println("boardLike = " + boardLike);
        System.out.println(boardLike.isPresent());

        //있으면 delete
        if (boardLike.isPresent()) {
            boardLikeRepository.delete(boardLike.get());
        } else {

            //없으면 insert
            BoardLike db = new BoardLike();
            Board board = boardRepository.findById(likeForm.getBoardId()).get();
            Member member = memberRepository.findById(authentication.getName()).get();
            db.setBoard(board);
            db.setMember(member);

            BoardLikeId id = new BoardLikeId();
            id.setBoardId(likeForm.getBoardId());
            id.setMemberEmail(authentication.getName());
            db.setId(id);
            boardLikeRepository.save(db);
        }

    }

    public BoardLikeDto get(Integer boardId, Authentication authentication) {
        Long count = boardLikeRepository.countByBoardId(boardId);
        Boolean liked = false;
        if (authentication != null) {
            var row = boardLikeRepository
                    .findByBoardIdAndMemberEmail(boardId, authentication.getName());
            liked = row.isPresent();
        }
        BoardLikeDto dto = new BoardLikeDto();
        dto.setCount(count);
        dto.setLiked(liked);

        return dto;
    }
}
