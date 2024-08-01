package com.pae.server.like.repository;

import com.pae.server.like.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    boolean existsByBoardIdAndMemberId(Long boardId, Long memberId);
    boolean existsById(Long likeId);

}
