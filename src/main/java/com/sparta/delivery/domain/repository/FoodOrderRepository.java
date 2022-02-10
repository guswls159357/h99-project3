package com.sparta.delivery.domain.repository;

import com.sparta.delivery.domain.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {
}
