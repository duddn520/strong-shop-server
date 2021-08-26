package com.strongshop.mobile.domain.Product.Items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PpfRepository extends JpaRepository<Ppf,Long> {
    Optional<Ppf> findByName(String name);
}
