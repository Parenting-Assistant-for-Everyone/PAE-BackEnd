package com.pae.server.board.service;


import com.pae.server.board.dto.request.InformationBoardReqDto;
import com.pae.server.board.dto.response.InformationBoardRespDto;

import java.io.IOException;
import java.util.List;

public interface InformationBoardService {
    void saveInformationBoard(InformationBoardReqDto informationBoardReqDto) throws IOException;
    List<InformationBoardRespDto> getAllInformationBoards();
    InformationBoardRespDto getInformationBoard(Long id);
}
