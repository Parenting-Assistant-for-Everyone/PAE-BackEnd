package com.pae.server.like.repository;

import com.pae.server.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByBoardIdAndMemberId(Long boardId, Long memberId);
    boolean existsByMemberId(Long memberId);
    boolean existsById(Long likeId);
    Optional<Like> findByBoardIdAndMemberId(Long boardId, Long memberId);

}
