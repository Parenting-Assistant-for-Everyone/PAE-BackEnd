package com.pae.server.image.repository;

import com.pae.server.image.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByBoardIdOrderByImageOrder(Long boardId);
}
