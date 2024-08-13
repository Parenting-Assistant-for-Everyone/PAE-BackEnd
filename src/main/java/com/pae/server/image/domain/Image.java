package com.pae.server.image.domain;

import com.pae.server.board.domain.Board;
import com.pae.server.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@Table(name = "Image")
public class Image extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private PhotoData photoData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Image(PhotoData photoData, Board board) {
        this.photoData = photoData;
        this.board = board;
    }

    public static Image of(PhotoData photoData, Board board) {
        return Image.builder()
                .photoData(photoData)
                .board(board)
                .build();
    }

    public String getBucket() {
        return photoData.getBucket();
    }

    public String getKey() {
        return photoData.getObject();
    }
}
