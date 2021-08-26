package com.strongshop.mobile.domain.Product.Items;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackBoxRepository extends JpaRepository<Blackbox,Long> {
    Optional<Blackbox> findByName(String name);
}
