package com.sparta.delivery.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodOrderDto {

    private String name; //음식 이름
    private Integer quantity;
    private Integer price;
}
