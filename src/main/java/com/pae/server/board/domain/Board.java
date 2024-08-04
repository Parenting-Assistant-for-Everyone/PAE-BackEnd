package com.pae.server.board.domain;

import com.pae.server.common.domain.BaseEntity;
import com.pae.server.common.enums.BaseStatus;
import com.pae.server.image.domain.Image;
import com.pae.server.like.domain.Like;
import com.pae.server.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Table(name = "board")
public abstract class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVATE'")
    private BaseStatus baseStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();
    @OneToMany(mappedBy = "board")
    private List<Like> likes = new ArrayList<>();

    @ColumnDefault("0")
    private Integer viewCount;

    public void setMember(Member member){
        this.member = member;
        member.getBoards().add(this);
    }
    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    protected void setBaseStatus(BaseStatus baseStatus) {
        this.baseStatus = baseStatus;
    }
    public void incrementViewCount(){
        this.viewCount+=1;
    }

    public Set<String> getAllImageNames() {
        return images.stream()
                .map(image -> image.getPhotoData().getOriginalName())
                .collect(Collectors.toSet());
    }

    public void deleteBoard() {
        this.baseStatus = baseStatus.DEACTIVATE;
    }
}
