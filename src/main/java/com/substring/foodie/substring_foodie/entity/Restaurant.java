package com.substring.foodie.substring_foodie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "foodie_restaurants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    private String id;

    private String name;

    @Lob
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean open = true;

    private String address;
    //TODO:
    private String banner;
    private LocalDateTime createdDate;



    @ManyToOne
    private User user;
}
