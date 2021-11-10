package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Company.Company;
import com.strongshop.mobile.domain.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BiddingRepository extends JpaRepository<Bidding,Long> {
    Optional<List<Bidding>> findAllByCompany(Company company);
    Optional<List<Bidding>> findAllByOrder(Order order);

}
