package com.substring.foodie.substring_foodie.service;

import com.substring.foodie.substring_foodie.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantService {

    RestaurantDto add(RestaurantDto restaurantDto);

    RestaurantDto update(RestaurantDto restaurantDto, String id);

    void delete(String id);

    //get single
    RestaurantDto get(String id);

    // get all
    Page<RestaurantDto> getAll(Pageable pageable);

    List<RestaurantDto> searchByName(String keyword);

    //extra service add these services
    Page<RestaurantDto> getOpenRestaurants(Pageable pageable);
}
