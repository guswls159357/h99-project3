package com.sparta.delivery.domain.service;

import com.sparta.delivery.domain.entity.Food;
import com.sparta.delivery.domain.entity.Restaurant;
import com.sparta.delivery.domain.repository.FoodRepository;
import com.sparta.delivery.domain.repository.RestaurantRepository;
import com.sparta.delivery.exception.FieldException;
import com.sparta.delivery.web.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    //음식 저장시 중복되면 안된다!!
    @Transactional
    public void create(List<FoodDto> foodDtos, Long restaurantId){

        //restaurantId 가 null이면 안됨
        if(restaurantId==null){
            throw new FieldException("restaurantId","null","가게 아이디를 입력해 주세요");
        }

        Restaurant findRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(() -> {
            throw new FieldException("restaurantId",restaurantId.toString() ,"존재하지 않는 가게입니다");
        });


        //foodDto에서 중복된 이름이 있는지 확인!
        checkDuplicateNameDtoList(foodDtos);
        //같은 음식점의 음식들을 모두 꺼내와 들어온 Dto들을 비교 후 저장
        List<String> restaurantFoodNames = foodRepository.findAllByRestaurantId(restaurantId)
                .stream().map(food -> food.getName()).collect(Collectors.toList());


        foodDtos.stream().forEach(foodDto -> {
            if(restaurantFoodNames.contains(foodDto.getName())){
                throw new FieldException("foodName",foodDto.getName(),"가게에 이미 등록된 음식입니다");
            }
            Food food = foodDto.toEntity(findRestaurant);
            foodRepository.save(food);
        });
    }

    private void checkDuplicateNameDtoList(List<FoodDto> foodDtos){

        List<String> foodNames = new ArrayList<>();
        foodDtos.stream().forEach(foodDto -> {
            String foodName = foodDto.getName();
            if(foodNames.contains(foodName)){
                throw new FieldException("foodName",foodName,"같은 이름의 음식은 등록하실 수 없습니다");
            }
            foodNames.add(foodName);

        });
    }


}
