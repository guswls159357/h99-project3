package com.sparta.delivery.domain.service;

import com.sparta.delivery.domain.repository.FoodRepository;
import com.sparta.delivery.domain.repository.RestaurantRepository;
import com.sparta.delivery.exception.FieldException;
import com.sparta.delivery.web.dto.FoodDto;
import com.sparta.delivery.web.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public RestaurantDto create(RestaurantDto dto){

        if(restaurantRepository.existsByName(dto.getName())){
            throw new FieldException("name",dto.getName(),"같은 이름의 음식점이 등록되어 있습니다");
        }

       return restaurantRepository.save(dto.toEntity()).toDto();
    }

    public List<RestaurantDto> getAll(){
        return restaurantRepository.findAll().stream().map(restaurant -> restaurant.toDto())
                .collect(Collectors.toList());
    }

    public List<FoodDto> getAllFoods(Long restaurantId){

        return foodRepository.findAllByRestaurantId(restaurantId)
                .stream().map(food -> food.toDto()).collect(Collectors.toList());
    }
}
