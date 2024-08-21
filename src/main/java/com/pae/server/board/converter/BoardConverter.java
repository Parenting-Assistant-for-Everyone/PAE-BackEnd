package com.pae.server.board.converter;

import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.response.CommentResDtoList;
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
        CommentResDtoList comments = CommentConverter.viewComments(matchingBoard.getComments());
        return UpdateMatchingBoardResDto.builder()
                .title(matchingBoard.getTitle())
                .content(matchingBoard.getContent())
                .baseStatus(matchingBoard.getBaseStatus())
                .boardType(matchingBoard.getBoardType())
                .viewCount(matchingBoard.getViewCount())
                .id(matchingBoard.getId())
                .updatedAt(formattedDate)
                .comment(comments)
                .comments(matchingBoard.getComments().size())
                .likes(matchingBoard.getLikes().size())
                .writer(matchingBoard.getBoardType()== BoardType.OFFER ? matchingBoard.getMember().getNickname(): matchingBoard.getAssistant().getName())
                .address(matchingBoard.getBoardType()== BoardType.OFFER ? matchingBoard.getMember().getAddress() : matchingBoard.getAssistant().getAddress())
                .latitude(matchingBoard.getBoardType()== BoardType.OFFER ? matchingBoard.getMember().getLatitude() : matchingBoard.getAssistant().getLatitude())
                .longitude(matchingBoard.getBoardType()== BoardType.OFFER ? matchingBoard.getMember().getLongitude() : matchingBoard.getAssistant().getLongitude())
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
