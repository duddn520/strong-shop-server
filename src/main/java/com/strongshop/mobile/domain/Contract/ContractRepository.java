package com.strongshop.mobile.domain.Contract;

import com.strongshop.mobile.domain.Bidding.Bidding;
import com.strongshop.mobile.domain.Order.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract,Long> {
    Optional<Contract> findByBidding(Bidding bidding);
    Optional<Contract> findByOrder(Order order);
    Optional<List<Contract>> findAllByCompanyId(Long id);
    Optional<List<Contract>> findAllByUserId(Long id);
}
