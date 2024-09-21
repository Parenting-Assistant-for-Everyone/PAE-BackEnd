package com.pae.server.board.domain;

import com.pae.server.board.domain.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String content;

    // 별점 평가
    private int rating;

    // 리뷰 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus status;

    public void update(String content, int rating, ReviewStatus status) {
        this.content = content;
        this.rating = rating;
        this.status = status;
    }
}
