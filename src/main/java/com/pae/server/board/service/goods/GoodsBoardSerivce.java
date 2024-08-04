package com.pae.server.board.service.goods;

import com.pae.server.board.dto.request.GoodsBoardModifyReqDto;
import com.pae.server.board.dto.request.GoodsBoardRegistReqDto;
import com.pae.server.board.dto.response.GoodsBoardRegistAndModifyRespDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GoodsBoardSerivce {
    GoodsBoardRegistAndModifyRespDto goodsBoardRegist(
            GoodsBoardRegistReqDto registDto,
            List<MultipartFile> images
    );

    GoodsBoardRegistAndModifyRespDto goodsBoardModify(
            Long boardId,
            GoodsBoardModifyReqDto modifyDto,
            List<MultipartFile> images
    );

    void goodsBoardDelete(
            Long boardId
    );
}
