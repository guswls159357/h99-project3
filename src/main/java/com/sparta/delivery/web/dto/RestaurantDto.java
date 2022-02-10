package com.sparta.delivery.web.dto;

import com.sparta.delivery.domain.entity.Restaurant;
import com.sparta.delivery.util.annotaion.DeliveryFeeUnit;
import com.sparta.delivery.util.annotaion.Valid100Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    private Long id;

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣0-9\\s]{1,20}$",
            message = "한글과 숫자로 이루어진 1~20글자 이내의 이름만 가능합니다")
    @NotBlank(message = "가게 이름을 입력해 주세요")
    private String name;

    @Valid100Unit
    @NotNull(message = "최소 주문 금액을 입력해 주세요")
    @Min(value = 1000, message = "최소 주문 금액은 1000-100000사이로 입력해 주세요")
    @Max(value = 100_000,message = "최소 주문 금액은 1000-100000사이로 입력해 주세요")
    //@Range(min=1000,max=1000_00,message = "최소 주문 금액은 1000-100000사이로 입력해 주세요")
    //rejectedvalue를 구하기 위해 변경
    private Integer minOrderPrice;

    @DeliveryFeeUnit
    @Min(value = 0, message = "배달 요금은 0-10000사이로 입력해 주세요")
    @Max(value = 100_00,message = "배달 요금은 0-10000사이로 입력해 주세요")
    //@Range(min = 0, max= 10000,message = "배달 요금은 0-10000사이로 입력해 주세요")
    //rejectedvalue를 구하기 위해 변경
    private Integer deliveryFee;

    public Restaurant toEntity(){
        return Restaurant.builder()
                .name(this.name)
                .deliveryFee(this.deliveryFee)
                .minOrderPrice(this.minOrderPrice)
                .build();
    }

}
