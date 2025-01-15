package com.substring.foodie.substring_foodie.repository;

import com.substring.foodie.substring_foodie.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {


    List<User> findByName(String userName);

    User findByEmail(String email);

    // :keyword -> Refers to the parameter passed dynamically at runtime.
    @Query("SELECT u FROM User u WHERE u.name LIKE %:keyword%")
    Page<User> findByNameContaining(@Param("keyword") String keyword, Pageable pageable);
}
