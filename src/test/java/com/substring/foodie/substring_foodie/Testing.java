package com.substring.foodie.substring_foodie;

import com.substring.foodie.substring_foodie.dto.ErrorResponse;
import com.substring.foodie.substring_foodie.dto.RestaurantDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class Testing {

    @Test
    void test() {
        ErrorResponse er = ErrorResponse.builder().message("Hello").status(HttpStatus.NOT_FOUND).build();
        System.out.println(er);
    }

    @Test
    void test1() {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setOpenTime(LocalTime.now());
        restaurantDto.setCloseTime(LocalTime.now().plusHours(10));
        System.out.println(restaurantDto.getCloseTime());
        System.out.println(restaurantDto.getOpenTime());

        LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

    }

}
