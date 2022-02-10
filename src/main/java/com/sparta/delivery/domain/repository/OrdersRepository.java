package com.sparta.delivery.domain.repository;

import com.sparta.delivery.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query("select distinct o from Orders o join fetch o.foodOrderList")
    List<Orders> findAllFetch();
}
