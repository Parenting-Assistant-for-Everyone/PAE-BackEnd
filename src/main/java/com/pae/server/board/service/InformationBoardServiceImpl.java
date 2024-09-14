package com.pae.server.board.service;

import com.pae.server.board.converter.InformationBoardConverter;
import com.pae.server.board.domain.InformationBoard;
import com.pae.server.board.dto.request.InformationBoardReqDto;
import com.pae.server.board.dto.response.InformationBoardRespDto;
import com.pae.server.board.repository.InformationBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InformationBoardServiceImpl implements InformationBoardService {


    private final InformationBoardRepository informationBoardRepository;

    // 정보 게시판 저장 : 크롤링 함수
    public void saveInformationBoard(InformationBoardReqDto informationBoardReqDto) throws IOException {
        // 링크 연결
        Document doc = Jsoup.connect(informationBoardReqDto.url()).get();
        // 제목
        String title = doc.title();

        // 내용
        String content = doc.text();

        // Post 엔티티로 변환하여 저장
        InformationBoard savedBoard = InformationBoardConverter.toEntity(informationBoardReqDto, title, content);
        informationBoardRepository.save(savedBoard);
    }


    // 정보게시판 게시글 조회
    public List<InformationBoardRespDto> getAllInformationBoards() {
        return informationBoardRepository.findAll().stream()
                .map(InformationBoardConverter::toResponseDto)
                .collect(Collectors.toList());
    }

    // 정보게시판 게시글 상세 조회
    public InformationBoardRespDto getInformationBoard(Long id) {
        return informationBoardRepository.findById(id)
                .map(InformationBoardConverter::toResponseDto)
                .orElseThrow(() -> new RuntimeException("InformationBoard not found"));
    }
}
