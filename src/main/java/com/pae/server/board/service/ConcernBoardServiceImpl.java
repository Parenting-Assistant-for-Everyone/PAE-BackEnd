package com.pae.server.board.service;

import com.pae.server.board.converter.ConcernBoardConverter;
import com.pae.server.board.domain.ConcernBoard;
import com.pae.server.board.dto.request.ConcernBoardReqDto;

import com.pae.server.board.dto.request.UpdateConcernBoardReqDto;
import com.pae.server.board.dto.response.ConcernBoardRespDto;
import com.pae.server.board.dto.response.UpdateConcernBoardRespDto;
import com.pae.server.board.repository.ConcernBoardRepository;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConcernBoardServiceImpl implements ConcernBoardService{

    private final ConcernBoardRepository concernBoardRepository;
    private final MemberRepository memberRepository;

    // 고민게시판 생성
    public ConcernBoardRespDto createConcernBoard(ConcernBoardReqDto concernBoardRequestDto) {

        ConcernBoard concernBoard = ConcernBoardConverter.toEntity(concernBoardRequestDto);
        ConcernBoard savedBoard = concernBoardRepository.save(concernBoard);
        return ConcernBoardConverter.toResponseDto(savedBoard);
    }

    // 고민게시판 리스트 조회
    public List<ConcernBoardRespDto> getAllConcernBoards() {
        return concernBoardRepository.findAll().stream()
                .map(ConcernBoardConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    // 고민게시판 상세 조회
    public ConcernBoardRespDto getConcernBoard(Long id) {
        ConcernBoard concernBoard = concernBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ConcernBoard not found"));

        // 조회수 증가
        concernBoard.incrementViewCount();
        concernBoardRepository.save(concernBoard);

        return ConcernBoardConverter.toResponseDto(concernBoard);

    }

    // 고민게시판 수정
    public UpdateConcernBoardRespDto updateConcernBoard(Long id, UpdateConcernBoardReqDto updateConcernBoardReqDto) {
        ConcernBoard concernBoard = concernBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ConcernBoard not found"));
        concernBoard.update(updateConcernBoardReqDto.title(), updateConcernBoardReqDto.content(), updateConcernBoardReqDto.baseStatus());
        ConcernBoard updatedBoard = concernBoardRepository.save(concernBoard);
        return ConcernBoardConverter.toUpdateResponseDto(updatedBoard);
    }

    // 고민게시판 삭제
    public void deleteConcernBoard(Long id) {
        concernBoardRepository.deleteById(id);
    }

    // 고민게시판 찜 목록 조회 (구현 미완성)
    public List<ConcernBoardRespDto> getLikeBoards(Member member) {
        return null;
    }

    // 고민게시판 내가 작성한 글 목록 조회
    public List<ConcernBoardRespDto> getMyBoards(Long memberId) {
        return null;
    }
}
