package com.sparta.delivery.domain.entity;

import com.sparta.delivery.web.dto.FoodDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "food",
        indexes = {
                @Index(name = "idx_food_created_at", columnList = "created_at"),
                @Index(name = "idx_food_updated_at", columnList = "updated_at")
        })
public class Food extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(name="name",nullable = false,columnDefinition = "varchar(20)")
    private String name; //같은 음식점일때만 중복 확인 추가

    @Column(name="price",nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "food", orphanRemoval = true)
    private List<FoodOrder> foodOrderList = new ArrayList<>();

    @Builder
    public Food(String name, Integer price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        setRestaurant(restaurant);
    }

    private void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.getFoodList().add(this);
    }

    public FoodDto toDto(){
        return FoodDto.builder()
                .id(this.getId())
                .name(this.getName())
                .price(this.getPrice())
                .build();
    }
}
