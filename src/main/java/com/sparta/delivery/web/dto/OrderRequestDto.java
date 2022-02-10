package com.sparta.delivery.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderRequestDto {

    @NotNull(message = "가게 아이디를 입력해 주세요")
    private Long restaurantId;

    @NotNull(message = "음식 주문 정보가 필요합니다")
    @Valid
    private List<FoodOrderRequestDto> foods;
}
