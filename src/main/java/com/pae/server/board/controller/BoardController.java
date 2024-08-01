package com.pae.server.board.controller;

import com.pae.server.board.converter.BoardConverter;
import com.pae.server.board.domain.Board;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.request.UpdateMatchingBoardDto;
import com.pae.server.board.dto.response.CreateMatchingBoardResDto;
import com.pae.server.board.dto.response.MatchingBoardListDto;
import com.pae.server.board.dto.response.UpdateMatchingBoardResDto;
import com.pae.server.board.service.BoardServiceImpl;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardServiceImpl boardService;

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
    public ApiResponse<MatchingBoardListDto> getMatchingBoardList(){
        List<MatchingBoard>  matchingBoardList = boardService.getMatchingBoardList();
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(matchingBoardList) ,CustomResponseStatus.SUCCESS);
    }
    //구직용 육아도우미 게시판 리스트 조회
    @GetMapping("/matchingBoardList/jobSearch")
    public ApiResponse<MatchingBoardListDto> getJobSearchList(){
        List<MatchingBoard> jobSearchList = boardService.getJobSearchList();
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(jobSearchList) ,CustomResponseStatus.SUCCESS);
    }
    //구인용 육아도우미 게시판 리스트 조회
    @GetMapping("/matchingBoardList/jobOpening")
    public ApiResponse<MatchingBoardListDto> getJobOpeningList(){
        List<MatchingBoard> jobOpenningList = boardService.getJobOpenningList();
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
    public ApiResponse<MatchingBoardListDto> getSearchResult(@RequestParam("keyword") String keyWord){
        List<MatchingBoard> searchResult = boardService.getSearchResult(keyWord);
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(searchResult),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 게시판 조회순 정렬
    @GetMapping("/getView")
    public ApiResponse<MatchingBoardListDto> getViewCountResult(){
        List<MatchingBoard> result = boardService.getViewCountResult();
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(result),CustomResponseStatus.SUCCESS);
    }
    //육아도우미 게시판 최신순 정렬
    @GetMapping("/getRecent")
    public ApiResponse<MatchingBoardListDto> getRecentResult(){
        List<MatchingBoard> result = boardService.getRecentResult();
        return ApiResponse.createSuccess(BoardConverter.getMatchingBoardList(result),CustomResponseStatus.SUCCESS);
    }

}
