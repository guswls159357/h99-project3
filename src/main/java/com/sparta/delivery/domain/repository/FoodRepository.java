package com.sparta.delivery.domain.repository;

import com.sparta.delivery.domain.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {


    @Query("select f from Food f join fetch f.restaurant where f.restaurant.id = :restaurantId")
    List<Food> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);


}
