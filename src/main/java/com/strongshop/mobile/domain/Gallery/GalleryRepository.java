package com.strongshop.mobile.domain.Gallery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery,Long> {
    List<Gallery> findAllByCompanyIdOrderByCreatedTimeDesc(Long id);
}
