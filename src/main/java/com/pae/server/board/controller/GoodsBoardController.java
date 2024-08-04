package com.pae.server.board.controller;

import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import com.pae.server.board.dto.request.GoodsBoardRegistReqDto;
import com.pae.server.board.dto.response.GoodsBoardRegistAndModifyRespDto;
import com.pae.server.board.service.goods.GoodsBoardSerivce;
import com.pae.server.common.dto.ApiResponse;
import com.pae.server.common.enums.CustomResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@Slf4j
public class GoodsBoardController {
    private final GoodsBoardSerivce goodsBoardSerivce;

    @PostMapping("/goods")
    public ResponseEntity<ApiResponse<GoodsBoardRegistAndModifyRespDto>> goodsBoardRegist(
            @RequestPart GoodsBoardRegistReqDto registDto,
            @RequestPart(required = false) List<MultipartFile> images
    ) {
        log.info("registDto : {}", registDto);
        GoodsBoardRegistAndModifyRespDto response = goodsBoardSerivce.goodsBoardRegist(registDto, images);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @PutMapping("goods/{goodsId}")
    public ResponseEntity<ApiResponse<GoodsBoardRegistAndModifyRespDto>> goodsBoardModify(
            @PathVariable Long goodsId,
            @RequestPart GoodsBoardModifyReqDto modifyReqDto,
            @RequestPart(required = false) List<MultipartFile> newImages
    ) {
        GoodsBoardRegistAndModifyRespDto response = goodsBoardSerivce.goodsBoardModify(goodsId, modifyReqDto, newImages);
        return ResponseEntity.ok().body(ApiResponse.createSuccess(response, CustomResponseStatus.SUCCESS));
    }

    @DeleteMapping("goods/{deleteGoodsId}")
    public ResponseEntity<ApiResponse<String>> goodsBoardDelete(
            @PathVariable Long deleteGoodsId
    ) {
        goodsBoardSerivce.goodsBoardDelete(deleteGoodsId);
        return ResponseEntity.ok().body(ApiResponse.createSuccess("게시글 삭제 완료", CustomResponseStatus.SUCCESS));
    }
}
