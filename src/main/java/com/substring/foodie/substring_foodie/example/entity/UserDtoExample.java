package com.substring.foodie.substring_foodie.example.entity;

import com.substring.foodie.substring_foodie.example.utils.ValidGender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoExample {

    //must contain one Caps Letter
    //must contain one digit
    //must contain one special character
    @NotEmpty(message = "Name is required !!")
    @Size(min = 3, max = 20, message = "Name must be between 2 and 20 characters")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "Invalid username. Username must contain 1 Capital Letter, 1 Digit and 1 Special Character")
    private String name;

    @Min(value = 18, message = "Minimum value required is 18")
    @Max(value = 99, message = "Maximum value required is 99")
    private int age;

    @Email(message = "Invalid email !!")
    private String email;

    @NotEmpty(message = "Password is required !!")
    private String password;

    @ValidGender(message = "Only male and female are allowed !!")
    private String gender;
}
