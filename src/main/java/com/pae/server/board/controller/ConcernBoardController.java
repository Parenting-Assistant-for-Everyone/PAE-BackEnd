package com.pae.server.board.controller;

import com.pae.server.board.dto.request.ConcernBoardReqDto;
import com.pae.server.board.dto.request.UpdateConcernBoardReqDto;
import com.pae.server.board.dto.response.ConcernBoardRespDto;
import com.pae.server.board.dto.response.UpdateConcernBoardRespDto;
import com.pae.server.board.service.ConcernBoardServiceImpl;
import com.pae.server.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/concern")
@Slf4j
public class ConcernBoardController {

    @Autowired
    ConcernBoardServiceImpl concernBoardService;

    // 고민게시판 생성
    @PostMapping("/post")
    public ResponseEntity<ConcernBoardRespDto> createConcernBoard(@RequestBody ConcernBoardReqDto concernBoardRequestDto) {
        try {
            ConcernBoardRespDto concernBoardRespDto = concernBoardService.createConcernBoard(concernBoardRequestDto);
            return ResponseEntity.ok(concernBoardRespDto);
        } catch (Exception e) {
            log.error("Error occurred while creating concern board: ", e); // 예외 로그 추가
            return ResponseEntity.internalServerError().build();
        }
    }

    //  고민게시판 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<ConcernBoardRespDto>> getAllConcernBoards() {
        return ResponseEntity.ok(concernBoardService.getAllConcernBoards());
    }

    // 고민게시판 상세 조회
    @GetMapping("/view/{Id}")
    public ResponseEntity<ConcernBoardRespDto> getConcernBoard(@PathVariable Long Id) {
        return ResponseEntity.ok(concernBoardService.getConcernBoard(Id));
    }

    // 고민게시판 수정
    @PutMapping("/update/{Id}")
    public ResponseEntity<UpdateConcernBoardRespDto> updateConcernBoard(@PathVariable Long Id, @RequestBody UpdateConcernBoardReqDto updateConcernBoardReqDto) {
        return ResponseEntity.ok(concernBoardService.updateConcernBoard(Id, updateConcernBoardReqDto));
    }

    // 고민게시판 삭제
    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Void> deleteConcernBoard(@PathVariable Long Id) {
        concernBoardService.deleteConcernBoard(Id);
        return ResponseEntity.noContent().build();
    }

    // 고민게시판 찜 목록 조회 (Service 구현 미완성)
    @GetMapping("/like")
    public ResponseEntity<List<ConcernBoardRespDto>> getLikeBoards(@RequestParam Member member) {
        List<ConcernBoardRespDto> favoriteBoards = concernBoardService.getLikeBoards(member);
        return ResponseEntity.ok(favoriteBoards);
    }

    // 고민게시판 내가 작성한 글 목록 조회 (Service 구현 미완성)
    @GetMapping("/list/{memberId}")
    public ResponseEntity<List<ConcernBoardRespDto>> getMyBoards(@PathVariable Long memberId) {
        return ResponseEntity.ok(concernBoardService.getMyBoards(memberId));
    }
}
