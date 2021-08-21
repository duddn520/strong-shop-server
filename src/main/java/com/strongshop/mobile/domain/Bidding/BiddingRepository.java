package com.strongshop.mobile.domain.Bidding;

import com.strongshop.mobile.domain.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BiddingRepository extends JpaRepository<Bidding,Long> {
    List<Bidding> findAllByOrder(Order order);

}
