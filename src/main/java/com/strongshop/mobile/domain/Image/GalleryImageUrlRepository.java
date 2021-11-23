package com.strongshop.mobile.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GalleryImageUrlRepository extends JpaRepository<GalleryImageUrl,Long> {
    Optional<List<GalleryImageUrl>> findAllByGalleryId(Long galleryId);
}
