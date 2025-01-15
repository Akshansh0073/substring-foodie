package com.substring.foodie.substring_foodie.entity;

import com.substring.foodie.substring_foodie.enums.UnitType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="foodie_foodItem")
public class FoodItem {

    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal basePrice;
    private UnitType unit;
    private String variationName;
    private BigDecimal weight;
    private Integer quantity;
    private BigDecimal availableStock;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
