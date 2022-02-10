package com.sparta.delivery.domain.repository;

import com.sparta.delivery.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {


    boolean existsByName(String name);


}
