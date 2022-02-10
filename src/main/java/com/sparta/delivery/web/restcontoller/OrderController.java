package com.sparta.delivery.web.restcontoller;

import com.sparta.delivery.domain.service.OrdersService;
import com.sparta.delivery.web.dto.OrderDto;
import com.sparta.delivery.web.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService orderService;

    @PostMapping("/order/request")
    public ResponseEntity request(@Validated @RequestBody OrderRequestDto orderRequestDto){


        OrderDto orderDto = orderService.create(orderRequestDto);

        return ResponseEntity
                .ok()
                .body(orderDto);
    }

    @GetMapping("/orders")
    public ResponseEntity allOrders(){

        List<OrderDto> allOrders = orderService.getAllOrders();
        return ResponseEntity
                .ok()
                .body(allOrders);
    }
}
