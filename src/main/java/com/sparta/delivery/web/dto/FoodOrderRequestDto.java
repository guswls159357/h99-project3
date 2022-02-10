package com.sparta.delivery.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FoodOrderRequestDto {

    @NotNull(message = "음식 아이디가 필요합니다")
    Long id; //음식 아이디

    @NotNull(message = "주문 수량이 필요합니다")
    @Min(value = 1, message = "주문 수량은 1-100이내로 입력해 주세요")
    @Max(value = 100, message = "주문 수량은 1-100이내로 입력해 주세요")
    Integer quantity;
}
