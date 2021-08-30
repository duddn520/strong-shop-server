package com.strongshop.mobile.domain.Product.Items;

import com.strongshop.mobile.domain.Company.Company;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PpfRepository extends JpaRepository<Ppf,Long> {
    Optional<List<Ppf>> findAllByCompany(Company company);
}
