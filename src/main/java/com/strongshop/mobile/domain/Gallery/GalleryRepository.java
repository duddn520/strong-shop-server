package com.strongshop.mobile.domain.Gallery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery,Long> {
    Optional<List<Gallery>> findAllByCompanyIdOrderByCreatedTimeAsc(Long companyId);
}
