package com.sparta.delivery.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {

    private String restaurantName;
    private List<FoodOrderDto> foods;
    private Integer deliveryFee;
    private Integer totalPrice;
}
