package com.strongshop.mobile.domain.Company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
    Optional<List<Company>> findAllByName(String name);
    Optional<Company> findByEmail(String email);
    Optional<Company> findByBusinessNumber(String businessNumber);

}
