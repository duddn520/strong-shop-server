package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByUser(User user);

}
