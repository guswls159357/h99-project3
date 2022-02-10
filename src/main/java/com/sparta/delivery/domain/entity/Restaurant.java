package com.sparta.delivery.domain.entity;

import com.sparta.delivery.web.dto.RestaurantDto;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "restaurant",
        indexes = {
                @Index(name = "idx_restaurant_created_at", columnList = "created_at"),
                @Index(name = "idx_restaurant_updated_at", columnList = "updated_at")
        })
public class Restaurant extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(unique = true,nullable = false,columnDefinition = "varchar(20)")
    private String name;

    @Column(nullable = false)
    private Integer minOrderPrice;

    @Column(nullable = false)
    private Integer deliveryFee;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant",orphanRemoval = true)
    private List<Food> foodList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant",orphanRemoval = true)
    private List<Orders> ordersList = new ArrayList<>();

    @Builder
    public Restaurant(String name, Integer minOrderPrice, Integer deliveryFee) {
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.deliveryFee = deliveryFee;
    }

    public RestaurantDto toDto(){
        return RestaurantDto.builder()
                .id(this.id)
                .name(this.name)
                .minOrderPrice(this.minOrderPrice)
                .deliveryFee(this.deliveryFee)
                .build();
    }
}
