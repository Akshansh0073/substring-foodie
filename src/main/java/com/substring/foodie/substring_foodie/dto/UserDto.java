package com.substring.foodie.substring_foodie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotEmpty(message = "Name is required !!")
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    private String name;

    @Email(message = "Invalid email !!")
    private String email;

    @NotEmpty(message = "Password is required !!")
    private String password;

    private String address;

    private String phoneNumber;

}
