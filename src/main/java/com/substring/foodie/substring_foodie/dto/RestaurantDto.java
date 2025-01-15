package com.substring.foodie.substring_foodie.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotEmpty(message = "Name is required !!")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm:ss a")
    private LocalTime openTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "hh:mm:ss a")
    private LocalTime closeTime;

    private Boolean open = true;

    @NotEmpty(message = "Address is required !!")
    @Size(min = 3, max = 100, message = "Address must be between 3 and 100 characters")
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime createdDate;


    //Write only json-object -- property included- de serialization
    //Read only  object-json -- property included- serialization
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    private String status="";

    @JsonIgnore
    private String banner;

    //Getter method
    @JsonProperty
    public String imageUrl() {
        return "http://localhost:8080/images/" + banner;
    }

}
