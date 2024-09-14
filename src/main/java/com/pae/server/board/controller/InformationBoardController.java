package com.pae.server.board.controller;

import com.pae.server.board.dto.request.InformationBoardReqDto;
import com.pae.server.board.dto.response.InformationBoardRespDto;
import com.pae.server.board.service.InformationBoardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/information")
@Slf4j
public class InformationBoardController {

    @Autowired
    InformationBoardServiceImpl informationBoardService;

    // 정보게시판 저장
    @PostMapping("/post")
    public ResponseEntity<String> saveInformationBoard(@RequestBody InformationBoardReqDto informationBoardReqDto) throws IOException {
        // 크롤링된 데이터 가져오기
        informationBoardService.saveInformationBoard(informationBoardReqDto);
        return ResponseEntity.ok("크롤링 완료 및 데이터 저장 성공");
    }

    // 정보 게시판 리스트 조회
    @GetMapping("/list")
    public ResponseEntity<List<InformationBoardRespDto>> getAllInformationBoards() {
        return ResponseEntity.ok(informationBoardService.getAllInformationBoards());
    }

    // 정보게시판 상세 조회
    @GetMapping("/view/{Id}")
    public ResponseEntity<InformationBoardRespDto> getInformationBoard(@PathVariable Long Id) {
        return ResponseEntity.ok(informationBoardService.getInformationBoard(Id));
    }



}
