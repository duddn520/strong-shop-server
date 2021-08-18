package com.strongshop.mobile.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyImageRepository extends JpaRepository<CompanyImage, Long> {

    List<CompanyImage> findAllByCompanyId(Long id);
}
