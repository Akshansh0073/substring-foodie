package com.substring.foodie.substring_foodie.service.ServiceImpl;

import com.substring.foodie.substring_foodie.Exception.ResourceNotFoundException;
import com.substring.foodie.substring_foodie.dto.RestaurantDto;
import com.substring.foodie.substring_foodie.entity.Restaurant;
import com.substring.foodie.substring_foodie.repository.RestaurantRepo;
import com.substring.foodie.substring_foodie.service.RestaurantService;
import com.substring.foodie.substring_foodie.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    private RestaurantRepo restaurantRepo;

    private ModelMapper modelMapper;

    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, ModelMapper modelMapper) {
        this.restaurantRepo = restaurantRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestaurantDto add (RestaurantDto restaurantDto) {
        restaurantDto.setId(Helper.generateRandomId());
        restaurantDto.setCreatedDate(LocalDateTime.now());
        Restaurant restaurant = modelMapper.map(restaurantDto, Restaurant.class);
        Restaurant savedRestaurant = restaurantRepo.save(restaurant);
        return modelMapper.map(savedRestaurant,RestaurantDto.class);
    }

    @Override
    public RestaurantDto update(RestaurantDto restaurantDto, String id) {

        Restaurant restaurant = restaurantRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Restaurant", "Restauarant_Id", id));
        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setOpenTime(restaurantDto.getOpenTime());
        restaurant.setCloseTime(restaurantDto.getCloseTime());
        restaurant.setOpen(restaurantDto.getOpen());
        restaurant.setAddress(restaurantDto.getAddress());
        Restaurant updatedRestaurant = restaurantRepo.save(restaurant);
        return modelMapper.map(updatedRestaurant,RestaurantDto.class);

    }

    @Override
    public void delete(String delete) {

        restaurantRepo.deleteById(delete);
    }

    @Override
    public RestaurantDto get(String id) {

        Restaurant restaurant = restaurantRepo.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Restaurant", "Restauarant_Id", id));
        return modelMapper.map(restaurant,RestaurantDto.class);

    }

    @Override
    public Page<RestaurantDto> getAll(Pageable pageable) {
        Page<Restaurant> allRestaurant = restaurantRepo.findAll(pageable);
        return allRestaurant.map
                (restaurant -> modelMapper.map(restaurant,RestaurantDto.class));
    }

    @Override
    public List<RestaurantDto> searchByName(String keyword) {

        List<Restaurant> byNameContaining = restaurantRepo.findByNameContaining(keyword);
        return byNameContaining.stream().map
                (restaurant -> modelMapper.map(restaurant,RestaurantDto.class)).toList();

    }

    @Override
    public Page<RestaurantDto> getOpenRestaurants(Pageable pageable) {

        Page<Restaurant> pageRestaurant = restaurantRepo.findByOpen(true, pageable);
        return pageRestaurant.
                map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class));

    }
}
