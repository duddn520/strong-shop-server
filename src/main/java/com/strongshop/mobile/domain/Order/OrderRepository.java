package com.strongshop.mobile.domain.Order;

import com.strongshop.mobile.domain.State;
import com.strongshop.mobile.domain.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<List<Order>> findAllByUser(User user);
    Optional<List<Order>> findAllByStateAndRegionOrderByCreatedTimeAsc(State state, String region);


}
