package com.pae.server.board.service;

import com.pae.server.board.dto.request.ConcernBoardReqDto;
import com.pae.server.board.dto.request.UpdateConcernBoardReqDto;
import com.pae.server.board.dto.response.ConcernBoardRespDto;
import com.pae.server.board.dto.response.UpdateConcernBoardRespDto;
import com.pae.server.member.domain.Member;

import java.util.List;


public interface ConcernBoardService {
    ConcernBoardRespDto createConcernBoard(ConcernBoardReqDto concernBoardRequestDto);
    List<ConcernBoardRespDto> getAllConcernBoards();
    ConcernBoardRespDto getConcernBoard(Long id);
    UpdateConcernBoardRespDto updateConcernBoard(Long id, UpdateConcernBoardReqDto updateConcernBoardReqDto);
    void deleteConcernBoard(Long id);
    List<ConcernBoardRespDto> getLikeBoards(Member member);
    List<ConcernBoardRespDto> getMyBoards(Long memberId);
}
