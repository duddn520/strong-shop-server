package com.strongshop.mobile.domain.Car;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<List<Car>> findAllByUserId(Long userId);
}
