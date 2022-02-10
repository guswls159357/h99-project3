package com.sparta.delivery.domain.entity;

import com.sparta.delivery.web.dto.FoodOrderDto;
import com.sparta.delivery.web.dto.OrderDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders",
        indexes = {
                @Index(name = "idx_orders_created_at", columnList = "created_at"),
                @Index(name = "idx_orders_updated_at", columnList = "updated_at")
        })
public class Orders extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orders", orphanRemoval = true)
    private List<FoodOrder> foodOrderList = new ArrayList<>();


    private Integer totalPrice;

    @Builder
    public Orders(Restaurant restaurant) {
        setRestaurant(restaurant);
    }

    private void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        restaurant.getOrdersList().add(this);
    }

    public void setTotalPrice(Integer totalPrice){
        this.totalPrice = totalPrice;
    }

    public OrderDto toDto(List<FoodOrderDto> foodOrderDtoList) {

        return OrderDto.builder()
                .restaurantName(this.restaurant.getName())
                .foods(foodOrderDtoList)
                .deliveryFee(this.restaurant.getDeliveryFee())
                .totalPrice(this.getTotalPrice())
                .build();
    }
}
