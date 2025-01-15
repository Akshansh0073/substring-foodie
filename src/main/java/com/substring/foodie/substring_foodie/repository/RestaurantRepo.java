package com.substring.foodie.substring_foodie.repository;

import com.substring.foodie.substring_foodie.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepo extends JpaRepository <Restaurant, String> {

        @Query("SELECT u FROM Restaurant u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        List<Restaurant> findByNameContaining(String keyword);

        Page<Restaurant> findByOpen(boolean flag, Pageable pageable);
}
