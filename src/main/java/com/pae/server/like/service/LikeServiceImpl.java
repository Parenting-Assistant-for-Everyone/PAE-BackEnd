package com.pae.server.like.service;

import com.pae.server.board.domain.Board;
import com.pae.server.board.dto.response.MatchingBoardListDto;
import com.pae.server.board.repository.BoardRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.like.converter.LikeConverter;
import com.pae.server.like.domain.Like;
import com.pae.server.like.dto.request.CreateLikeReqDto;
import com.pae.server.like.repository.LikeRepository;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    @Override
    @Transactional
    public Like createLike(CreateLikeReqDto dto) {
        Board board = boardRepository.findById(dto.id()).orElseThrow(() -> new CustomException(CustomResponseStatus.BOARD_NOT_FOUND));
        Member member = memberRepository.findById(dto.memberId()).orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));

        if(likeRepository.existsByBoardIdAndMemberId(dto.id(),dto.memberId())){
            throw new CustomException(CustomResponseStatus.ALREADY_LIKED);
        }

        Like like = LikeConverter.createLike();
        like.setBoard(board);
        like.setMember(member);
        likeRepository.save(like);
        return like;
    }


    @Override
    public Like deleteLike(Long memberId, Long boardId) {
        if(!likeRepository.existsByBoardIdAndMemberId(boardId, memberId)){
            throw new CustomException(CustomResponseStatus.LIKE_NOT_FOUND);
        }
        else{
            Like like = likeRepository.findByBoardIdAndMemberId(boardId, memberId).get();
            likeRepository.delete(like);
            return like;
        }
    }

    @Override
    public List<MatchingBoardListDto> getLikeBoard() {
        return null;
    }

    @Override
    public boolean checkLike(Long id, Long memberId) {
        return likeRepository.existsByBoardIdAndMemberId(id, memberId);
    }
}
