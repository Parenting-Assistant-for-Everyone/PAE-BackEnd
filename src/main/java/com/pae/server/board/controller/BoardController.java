package com.pae.server.board.controller;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.board.converter.BoardConverter;
import com.pae.server.board.domain.Board;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.request.UpdateMatchingBoardDto;
import com.pae.server.board.dto.response.*;
import com.pae.server.board.service.BoardService;
import com.pae.server.board.service.BoardServiceImpl;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.member.converter.MemberConverter;
import com.pae.server.member.domain.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:19000") // React Native 앱의 URL을 여기에 추가합니다.

public class BoardController {
    private final BoardService boardService;

    //육아 도우미 게시판 생성
    @PostMapping("/create")
    public ApiResponse<CreateMatchingBoardResDto> create(@RequestBody @Valid CreateMatchingBoardDto dto){
        MatchingBoard matchingBoard = boardService.createMatchingBoard(dto);
        return ApiResponse.createSuccess(BoardConverter.createMatchingBoardDto(matchingBoard), CustomResponseStatus.SUCCESS);
    }
    //육아 도우미 게시판 수정
    @PutMapping("/update/{boardId}")
    public ApiResponse<UpdateMatchingBoardResDto> update(@PathVariable(name = "boardId") Long boardId,
                                                         @RequestBody @Valid UpdateMatchingBoardDto dto){
        MatchingBoard matchingBoard = boardService.updateMatchingBoard(boardId, dto);
        return ApiResponse.createSuccess(BoardConverter.updateMatchingBoardResDto(matchingBoard),CustomResponseStatus.SUCCESS);
    }

    // 육아도우미 게시판 삭제
    @DeleteMapping("/delete/{boardId}")
    public ApiResponse<UpdateMatchingBoardResDto> delete(@PathVariable(name = "boardId") Long boardId){
        MatchingBoard matchingBoard = boardService.deleteMatchingBoard(boardId);
        return ApiResponse.createSuccess(BoardConverter.updateMatchingBoardResDto(matchingBoard),CustomResponseStatus.BOARD_DELETE);
    }

    //육아 도우미 게시판 리스트 조회
    @GetMapping("/matchingBoardList")
    public ApiResponse<MatchingBoardListDto> getMatchingBoardList(@RequestParam(name="page")Integer page){
        Page<MatchingBoard> matchingBoardList = boardService.getMatchingBoardList(page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(matchingBoardList) ,CustomResponseStatus.SUCCESS);
    }
    //구직용 육아도우미 게시판 리스트 조회
    @GetMapping("/matchingBoardList/jobSearch")
    public ApiResponse<MatchingBoardListDto> getJobSearchList(@RequestParam(name="page")Integer page){
        Page<MatchingBoard> jobSearchList = boardService.getJobSearchList(page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(jobSearchList) ,CustomResponseStatus.SUCCESS);
    }
    //구인용 육아도우미 게시판 리스트 조회
    @GetMapping("/matchingBoardList/jobOpening")
    public ApiResponse<MatchingBoardListDto> getJobOpeningList(@RequestParam(name="page")Integer page){
        Page<MatchingBoard> jobOpenningList = boardService.getJobOpenningList(page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(jobOpenningList) ,CustomResponseStatus.SUCCESS);
    }

    //육아도우미 게시판 상세내용 확인
    @GetMapping("/view/{boardId}")
    public ApiResponse<UpdateMatchingBoardResDto> getMatchingBoard(@PathVariable(name = "boardId") Long boardId){
        MatchingBoard matchingBoard = boardService.getMatchingBoard(boardId);
        matchingBoard.incrementViewCount();
        return ApiResponse.createSuccess(BoardConverter.updateMatchingBoardResDto(matchingBoard),CustomResponseStatus.SUCCESS);
    }

    //육아도우미 게시판 검색 기능
    @GetMapping("/search")
    public ApiResponse<MatchingBoardListDto> getSearchResult(@RequestParam("keyword") String keyWord, @RequestParam(name="page")Integer page){
        Page<MatchingBoard> searchResult = boardService.getSearchResult(keyWord, page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(searchResult),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 게시판 조회순 정렬
    @GetMapping("/getView")
    public ApiResponse<MatchingBoardListDto> getViewCountResult(@RequestParam(name="page")Integer page){
        Page<MatchingBoard> result = boardService.getViewCountResult(page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(result),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 게시판 최신순 정렬
    @GetMapping("/getRecent")
    public ApiResponse<MatchingBoardListDto> getRecentResult(@RequestParam(name="page")Integer page){
        Page<MatchingBoard> result = boardService.getRecentResult(page);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(result),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 구인글 작성자 프로필 조회
    @GetMapping("/getMemberProfile/{boardId}")
    public ApiResponse<MemberProfileResDto> getMemberProfile(@PathVariable(name = "boardId")Long boardId){
        Member member = boardService.getMemberProfile(boardId);
        return ApiResponse.createSuccess(MemberConverter.getMemberProfile(member),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 구직글 작성자 프로필 조회
    @GetMapping("/getAssistantProfile/{boardId}")
    public ApiResponse<AssistantProfileResDto> getAssistantProfile(@PathVariable(name="boardId")Long boardId){
        Assistant assistant = boardService.getAssistantProfile(boardId);
        return ApiResponse.createSuccess(MemberConverter.getAssistantProfile(assistant),CustomResponseStatus.SUCCESS);
    }

}
