package com.strongshop.mobile.domain.Product;

import com.strongshop.mobile.domain.Company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<List<Product>> findAllByCompanyId(Long companyId);
    Optional<List<Product>> findAllByItemAndCompanyId(Item item,Long companyId);
}
