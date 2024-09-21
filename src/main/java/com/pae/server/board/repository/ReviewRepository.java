package com.pae.server.board.repository;

import com.pae.server.board.domain.Review;
import com.pae.server.board.domain.enums.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByStatus(ReviewStatus status);
}
