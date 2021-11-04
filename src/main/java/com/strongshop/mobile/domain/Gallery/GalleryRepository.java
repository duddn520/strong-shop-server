package com.strongshop.mobile.domain.Gallery;

import com.strongshop.mobile.domain.Image.GalleryImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery,Long> {
    Optional<List<Gallery>> findAllByCompanyId(Long companyId);
}
