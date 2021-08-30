package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlackBoxRepository extends JpaRepository<Blackbox,Long> {
    Optional<List<Blackbox>> findAllByCompany(Company company);
}
