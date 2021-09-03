package com.strongshop.mobile.domain.Gallary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GallaryRepository extends JpaRepository<Gallary,Long> {
    List<Gallary> findAllByCompanyIdOrderByCreatedTimeDesc(Long id);
}
