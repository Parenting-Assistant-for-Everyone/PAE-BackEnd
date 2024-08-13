package com.pae.server.board.service;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.request.UpdateMatchingBoardDto;
import com.pae.server.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardService {
    MatchingBoard createMatchingBoard(CreateMatchingBoardDto dto); //육아도우미 게시판 생성
    MatchingBoard updateMatchingBoard(Long boardId, UpdateMatchingBoardDto dto); //육아도우미 게시판 수정
    MatchingBoard deleteMatchingBoard(Long boardId); //육아도우미 게시판 삭제
    Page<MatchingBoard> getMatchingBoardList(Integer page); //육아도우미 게시판 목록 조회
    Page<MatchingBoard> getJobSearchList(Integer page); //구직용 육아도우미 게시판 조회
    Page<MatchingBoard> getJobOpenningList(Integer page); //구인용 육아도우미 게시판 조회
    MatchingBoard getMatchingBoard(Long boardId);
    Page<MatchingBoard> getSearchResult(String keyWord,Integer page);
    Page<MatchingBoard> getViewCountResult(Integer page);
    Page<MatchingBoard> getRecentResult(Integer page);
    Member getMemberProfile(Long boardId);//육아도우미 구인 게시판 글쓴이 프로필 조회
    Assistant getAssistantProfile(Long boardId); //육아도임 구직 게시판 글쓴이 프로필 조회
}
