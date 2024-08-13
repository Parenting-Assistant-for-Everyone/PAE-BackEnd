package com.pae.server.board.converter;

import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.response.CreateMatchingBoardResDto;
import com.pae.server.board.dto.response.MatchingBoardListDto;
import com.pae.server.board.dto.response.UpdateMatchingBoardResDto;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BoardConverter {

    //육아도우미 생성 converter
    public static MatchingBoard createMatchingBoard(CreateMatchingBoardDto dto){
        return MatchingBoard.builder()
                .title(dto.title())
                .content(dto.content())
                .boardType(dto.boardType())
                .baseStatus(dto.baseStatus())
                .viewCount(0)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
    public static CreateMatchingBoardResDto createMatchingBoardDto(MatchingBoard matchingBoard){
        return CreateMatchingBoardResDto.builder()
                .baseStatus(matchingBoard.getBaseStatus())
                .boardType(matchingBoard.getBoardType())
                .title(matchingBoard.getTitle())
                .content(matchingBoard.getContent()).
                viewCount(matchingBoard.getViewCount())
                .id(matchingBoard.getId())
                .build();
    }
    public static UpdateMatchingBoardResDto updateMatchingBoardResDto(MatchingBoard matchingBoard){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = matchingBoard.getModifiedAt().format(formatter);
        return UpdateMatchingBoardResDto.builder()
                .title(matchingBoard.getTitle())
                .content(matchingBoard.getContent())
                .baseStatus(matchingBoard.getBaseStatus())
                .boardType(matchingBoard.getBoardType())
                .viewCount(matchingBoard.getViewCount())
                .id(matchingBoard.getId())
                .updatedAt(formattedDate)
                .comments(matchingBoard.getComments().size())
                .likes(matchingBoard.getLikes().size())
                .writer(matchingBoard.getMember().getNickname())
                .build();
    }
    public static MatchingBoardListDto getMatchingBoardList(Page<MatchingBoard> list){
        List<UpdateMatchingBoardResDto> listDto = list.stream()
                .map(BoardConverter::updateMatchingBoardResDto).collect(Collectors.toList());
        return MatchingBoardListDto.builder()
                .boardList(listDto)
                .size(list.getSize())
                .totalElement(list.getTotalElements())
                .totalPages(list.getTotalPages())
                .build();

    }
}
