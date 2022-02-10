package com.sparta.delivery.web.dto;

import com.sparta.delivery.domain.entity.Food;
import com.sparta.delivery.domain.entity.Restaurant;
import com.sparta.delivery.util.annotaion.Valid100Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDto {

    private Long id;
    @NotBlank(message = "음식 이름을 입력해 주세요")
    @Size(min=2,max = 10,message = "음식 이름은 2-10글자 이내로 입력해 주세요")
    private String name;

    @Min(value = 100, message = "음식 가격은 100-1000000 이내로 입력해 주세요")
    @Max(value = 1_000_000,message = "음식 가격은 100-1000000 이내로 입력해 주세요")
    @Valid100Unit(message = "음식 가격은 100원 단위로 입력해주세요")
    private Integer price;

    public Food toEntity(Restaurant restaurant){
        return Food.builder()
                .name(this.name)
                .price(this.price)
                .restaurant(restaurant)
                .build();
    }
}
