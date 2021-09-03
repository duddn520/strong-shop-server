package com.strongshop.mobile.domain.Gallary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GallaryImageRepository extends JpaRepository<GallaryImage,Long> {

    List<GallaryImage> findAllByGallaryId(Long id);
}
