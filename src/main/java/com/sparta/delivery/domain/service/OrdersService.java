package com.sparta.delivery.domain.service;

import com.sparta.delivery.domain.entity.Food;
import com.sparta.delivery.domain.entity.FoodOrder;
import com.sparta.delivery.domain.entity.Orders;
import com.sparta.delivery.domain.entity.Restaurant;
import com.sparta.delivery.domain.repository.FoodOrderRepository;
import com.sparta.delivery.domain.repository.FoodRepository;
import com.sparta.delivery.domain.repository.OrdersRepository;
import com.sparta.delivery.domain.repository.RestaurantRepository;
import com.sparta.delivery.exception.FieldException;
import com.sparta.delivery.web.dto.FoodOrderDto;
import com.sparta.delivery.web.dto.OrderDto;
import com.sparta.delivery.web.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final FoodOrderRepository foodOrderRepository;
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public OrderDto create(OrderRequestDto orderRequestDto) {

        Long restaurantId = orderRequestDto.getRestaurantId();
        List<Integer> priceList = new ArrayList<>();
        List<FoodOrderDto> foodOrderDtoList = new ArrayList<>();

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new FieldException("restaurantId", restaurantId.toString(), "음식점 정보를 확인해 주세요"));

        //order 저장
        Orders orders = Orders.builder()
                .restaurant(restaurant)
                .build();

        ordersRepository.save(orders);

        orderRequestDto.getFoods().stream().forEach(foodOrderRequestDto -> {

            //foodOrder를 만들기 위해 food를 불러오고, foodId와 quantity를 변수에 초기화
            Long foodId = foodOrderRequestDto.getId();
            Integer quantity = foodOrderRequestDto.getQuantity();
            log.info("quantity:{}",quantity);
            Food food = foodRepository.findById(foodId)
                    .orElseThrow(() -> new FieldException("foodId", foodId.toString(), "음식 정보를 확인해 주세요"));

            //foodOrder 생성 후 저장
            FoodOrder foodOrder = FoodOrder.builder()
                    .food(food)
                    .orders(orders)
                    .quantity(quantity)
                    .build();

            foodOrderRepository.save(foodOrder);

            //한 메뉴에 대한 총 주문 금액
            Integer oneMenuTotalPrice = quantity * food.getPrice();

            //결과를 리턴하기 위한 foodOrderDto 생성
            FoodOrderDto foodOrderDto = FoodOrderDto.builder()
                    .name(food.getName())
                    .quantity(quantity)
                    .price(oneMenuTotalPrice)
                    .build();

            //생성 후 결과값에 들어갈 리스트에 넣음
            foodOrderDtoList.add(foodOrderDto);
            //총 주문 금액을 계산하기 위해 저장
            priceList.add(oneMenuTotalPrice);
        });

        String restaurantName = restaurant.getName();
        //총 주문 금액 계산
        Integer totalPrice = priceList.stream().reduce(0, Integer::sum);
        Integer deliveryFee = restaurant.getDeliveryFee();
        if(totalPrice < restaurant.getMinOrderPrice()){
            StringBuilder msg = new StringBuilder();
            msg.append("총 주문 금액은 ");
            msg.append(restaurant.getMinOrderPrice().toString());
            msg.append(" 이상이여야 합니다");
            throw new FieldException("totalPrice",totalPrice.toString(),msg.toString());
        }
        orders.setTotalPrice(totalPrice+deliveryFee);

        return OrderDto.builder()
                .restaurantName(restaurantName)
                .foods(foodOrderDtoList)
                .deliveryFee(deliveryFee)
                .totalPrice(totalPrice+deliveryFee)
                .build();
    }

    public List<OrderDto> getAllOrders(){

        List<OrderDto> orderDtoList = new ArrayList<>();

        List<Orders> orders = ordersRepository.findAllFetch();

        orders.stream().forEach(order -> {

            List<FoodOrderDto> foodOrderDtoList = order.getFoodOrderList().stream().map(foodOrder -> foodOrder.toDto())
                    .collect(Collectors.toList());

            orderDtoList.add(order.toDto(foodOrderDtoList));

        });

        return orderDtoList;
    }


}
