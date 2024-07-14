package com.pae.server.board.service;

import com.pae.server.board.converter.BoardConverter;
import com.pae.server.board.domain.MatchingBoard;
import com.pae.server.board.domain.enums.BoardType;
import com.pae.server.board.dto.request.CreateMatchingBoardDto;
import com.pae.server.board.dto.request.UpdateMatchingBoardDto;
import com.pae.server.board.repository.BoardRepository;
import com.pae.server.board.repository.MatchingBoardRepository;
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


}
