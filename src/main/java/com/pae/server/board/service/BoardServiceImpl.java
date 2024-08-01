package com.pae.server.board.service;

import com.pae.server.assistant.domain.Assistant;
import com.pae.server.assistant.repository.AssistantRepository;
import com.pae.server.board.converter.BoardConverter;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.request.UpdateMatchingBoardDto;
import com.pae.server.board.repository.BoardRepository;
import com.pae.server.board.repository.MatchingBoardRepository;
import com.pae.server.common.enums.CustomResponseStatus;
import com.pae.server.common.exception.CustomException;
import com.pae.server.member.domain.Member;
import com.pae.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final MatchingBoardRepository matchingBoardRepository;
    private final MemberRepository memberRepository;
    private final AssistantRepository assistantRepository;
    @Override
    @Transactional
    public MatchingBoard createMatchingBoard(CreateMatchingBoardDto dto) {
        MatchingBoard matchingBoard = BoardConverter.createMatchingBoard(dto);
        matchingBoard.setMember(memberRepository.getById(dto.memberId()));
        return boardRepository.save(matchingBoard);
    }

    @Override
    @Transactional
    public MatchingBoard updateMatchingBoard(Long boardId, UpdateMatchingBoardDto dto) {
        MatchingBoard matchingBoard = (MatchingBoard) boardRepository.findById(boardId).get();
        matchingBoard.update(dto.title(),dto.content(),dto.baseStatus());
        return boardRepository.save(matchingBoard);
    }
    @Override
    @Transactional
    public MatchingBoard deleteMatchingBoard(Long boardId){
        MatchingBoard matchingBoard = (MatchingBoard) boardRepository.findById(boardId).get();
        boardRepository.delete(matchingBoard);
        return matchingBoard;

    }
    @Override
    public List<MatchingBoard> getMatchingBoardList() {
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findAll();
        return matchingBoardList;
    }

    //구직용 육아도우미 게시판 조회 메서드
    @Override
    public List<MatchingBoard> getJobSearchList() {
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findAllByBoardType(BoardType.SEARCH);
        return matchingBoardList;
    }

    @Override
    public List<MatchingBoard> getJobOpenningList() {
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findAllByBoardType(BoardType.OFFER);
        return matchingBoardList;    }

    @Override
    public MatchingBoard getMatchingBoard(Long boardId) {
        return matchingBoardRepository.findById(boardId).get();
    }

    //육아도우미 게시판 검색 기능
    @Override
    public List<MatchingBoard> getSearchResult(String keyWord) {
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findByTitleContainingOrContentContaining(keyWord, keyWord);
        return matchingBoardList;
    }
    //육아도우미 게시판 조회순 정렬
    @Override
    public List<MatchingBoard> getViewCountResult(){
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findAll(Sort.by(Sort.Direction.DESC,"viewCount"));
        return matchingBoardList;
    }
    //육아도우미 게시판 최신순 정렬
    @Override
    public List<MatchingBoard> getRecentResult() {
        List<MatchingBoard> matchingBoardList = matchingBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return matchingBoardList;
    }

    @Override
    public Member getMemberProfile(Long boardId) {
        if(!matchingBoardRepository.existsById(boardId)){
            throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);
        }
        else{
            MatchingBoard matchingBoard = matchingBoardRepository.findById(boardId).get();
            if(matchingBoard.getBoardType()!=BoardType.OFFER){
                throw new CustomException(CustomResponseStatus.NOT_OFFER_BOARD);
            }
            else{
                Member member = memberRepository.findById(matchingBoard.getMember().getId())
                        .orElseThrow(() -> new CustomException(CustomResponseStatus.MEMBER_NOT_FOUND));
                return member;
            }
        }
    }

    @Override
    public Assistant getAssistantProfile(Long boardId) {
        if(!matchingBoardRepository.existsById(boardId)){
            throw new CustomException(CustomResponseStatus.BOARD_NOT_FOUND);
        }
        else{
            MatchingBoard matchingBoard = matchingBoardRepository.findById(boardId).get();
            if(matchingBoard.getBoardType()!=BoardType.OFFER){
                throw new CustomException(CustomResponseStatus.NOT_OFFER_BOARD);
            }
            else{
                Assistant assistant = assistantRepository.findById(matchingBoard.getMember().getId())
                        .orElseThrow(() -> new CustomException(CustomResponseStatus.ASSISTANT_NOT_FOUND));
                return assistant;
            }
        }

    }


}
