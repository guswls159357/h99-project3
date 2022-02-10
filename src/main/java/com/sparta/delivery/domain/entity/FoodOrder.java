package com.sparta.delivery.domain.entity;

import com.sparta.delivery.web.dto.FoodOrderDto;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "foodOrder",
        indexes = {
                @Index(name = "idx_foodOrder_created_at", columnList = "created_at"),
                @Index(name = "idx_foodOrder_updated_at", columnList = "updated_at")
        })
public class FoodOrder extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Food food;

    @Column(nullable = false)
    private Integer quantity;

    @Builder
    public FoodOrder(Orders orders, Food food, Integer quantity) {
        this.orders = orders;
        this.quantity = quantity;
        setFood(food);
        setOrders(orders);
    }

    private void setFood(Food food){
        this.food = food;
        food.getFoodOrderList().add(this);
    }

    private void setOrders(Orders orders){
        this.orders = orders;
        orders.getFoodOrderList().add(this);
    }

    public FoodOrderDto toDto(){
        return FoodOrderDto.builder()
                .name(this.food.getName())
                .price(this.food.getPrice() * this.quantity)
                .quantity(this.quantity)
                .build();
    }
}
