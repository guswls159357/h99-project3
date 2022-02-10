package com.sparta.delivery.web.restcontoller;

import com.sparta.delivery.domain.service.FoodService;
import com.sparta.delivery.domain.service.RestaurantService;
import com.sparta.delivery.exception.FieldException;
import com.sparta.delivery.util.validator.CustomCollectionValidator;
import com.sparta.delivery.web.dto.FoodDto;
import com.sparta.delivery.web.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final CustomCollectionValidator validator;
    private final RestaurantService restaurantService;
    private final FoodService foodService;

    @PostMapping("/restaurant/register")
    public ResponseEntity register(@Validated @RequestBody RestaurantDto dto){

        RestaurantDto savedDto = restaurantService.create(dto);

        return ResponseEntity
                .ok()
                .body(savedDto);
    }

    @GetMapping("/restaurants")
    public ResponseEntity getAll(){

        return ResponseEntity
                .ok()
                .body(restaurantService.getAll());
    }

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public ResponseEntity saveFoods(@PathVariable("restaurantId") Long restaurantId,
                                    @Validated @RequestBody List<FoodDto> foodDtos,
                                    BindingResult bindingResult) throws BindException {

        validator.validate(foodDtos,bindingResult);
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        foodService.create(foodDtos,restaurantId);

        return ResponseEntity
                .ok()
                .body(null);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public ResponseEntity getFoodsInRestaurant(@PathVariable("restaurantId") Long restaurantId){

        List<FoodDto> foods = restaurantService.getAllFoods(restaurantId);

        return ResponseEntity
                .ok()
                .body(foods);
    }
}
