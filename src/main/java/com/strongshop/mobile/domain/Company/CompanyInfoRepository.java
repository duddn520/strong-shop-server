package com.strongshop.mobile.domain.Company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, Long> {
    Optional<CompanyInfo> findByCompanyId(Long companyId);
}
