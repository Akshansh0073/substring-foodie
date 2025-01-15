package com.substring.foodie.substring_foodie.Controller;

import com.substring.foodie.substring_foodie.config.AppConstants;
import com.substring.foodie.substring_foodie.dto.RestaurantDto;
import com.substring.foodie.substring_foodie.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    //add
    @PostMapping("/")
    public ResponseEntity<RestaurantDto> add(@Valid @RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.add(restaurantDto));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> update(
            @Valid @RequestBody RestaurantDto restaurantDto,
            @PathVariable String restaurantId)
    {
        return ResponseEntity.status(HttpStatus.OK).body
                (restaurantService.update(restaurantDto, restaurantId));
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.PAGE_SIZE) int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(restaurantService.getAll(pageable));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> getById(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.get(restaurantId));
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteById(@PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body("Restaurant deleted successfully");
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<RestaurantDto>> searchByNameContaining(@PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.searchByName(keyword));
    }

    @GetMapping("/open")
    public ResponseEntity<Page<RestaurantDto>> getOpenRestaurants(
            @RequestParam(value = "page", required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = AppConstants.PAGE_SIZE) int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(restaurantService.getOpenRestaurants(pageable));
    }

}
