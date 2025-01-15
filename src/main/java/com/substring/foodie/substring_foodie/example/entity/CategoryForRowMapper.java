package com.substring.foodie.substring_foodie.example.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryForRowMapper {

    private int id;
    private String title;
    private String description;
}
