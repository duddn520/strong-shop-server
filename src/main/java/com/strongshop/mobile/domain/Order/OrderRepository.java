package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.Bid.Bidding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
