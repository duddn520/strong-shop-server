package com.strongshop.mobile.domain.Contract;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompletedContractRepository extends JpaRepository<CompletedContract,Long> {
    Optional<List<CompletedContract>> findAllByUserId(Long userId);

}
