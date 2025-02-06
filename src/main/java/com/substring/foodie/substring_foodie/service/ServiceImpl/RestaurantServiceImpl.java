package com.substring.foodie.substring_foodie.service.ServiceImpl;

import com.substring.foodie.substring_foodie.Exception.ResourceNotFoundException;
import com.substring.foodie.substring_foodie.dto.FileData;
import com.substring.foodie.substring_foodie.dto.RestaurantDto;
import com.substring.foodie.substring_foodie.entity.Restaurant;
import com.substring.foodie.substring_foodie.repository.RestaurantRepo;
import com.substring.foodie.substring_foodie.service.FileService;
import com.substring.foodie.substring_foodie.service.RestaurantService;
import com.substring.foodie.substring_foodie.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Value("${restaurant.file.path}")
    private String bannerFolderPath;

    private RestaurantRepo restaurantRepo;

    private ModelMapper modelMapper;

    private FileService fileService;


    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, ModelMapper modelMapper, FileService fileService) {
        this.restaurantRepo = restaurantRepo;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
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
    public List<RestaurantDto> addList(List<RestaurantDto> restaurantDto) {

        List<RestaurantDto> collect = restaurantDto.stream().map(dto -> {
            dto.setId(UUID.randomUUID().toString());
            dto.setCreatedDate(LocalDateTime.now());
            return dto;
        }).toList();


        List<Restaurant> collect1 = collect.stream().map(dto -> modelMapper.map(dto,Restaurant.class)).
                collect(Collectors.toList());

        List<Restaurant> restaurants = restaurantRepo.saveAll(collect1);

        return restaurants.stream().map(restaurant->modelMapper.map(restaurant,RestaurantDto.class)).
                collect(Collectors.toList());
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
                orElseThrow(() -> new ResourceNotFoundException("Restaurant", "restauarant_Id", id));
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

    @Override
    public RestaurantDto uploadBanner(MultipartFile file, String restaurant_id) throws IOException {

        //abc.png
        String fileName = file.getOriginalFilename();

        //.png
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        //new name with date extension
        String newFileName = new Date().getTime() + fileExtension;

        //upload file to folder
        FileData fileData = fileService.uploadFile(file, bannerFolderPath + newFileName);


        Restaurant restaurant = restaurantRepo.findById(restaurant_id).orElseThrow
                (() -> new ResourceNotFoundException("Not found restaurant"));

        restaurant.setBanner(fileData.fileName());
        Restaurant save = restaurantRepo.save(restaurant);

        return modelMapper.map(save,RestaurantDto.class);
    }
}
