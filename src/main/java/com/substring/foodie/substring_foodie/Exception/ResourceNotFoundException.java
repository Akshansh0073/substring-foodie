package com.substring.foodie.substring_foodie.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String value;

    public ResourceNotFoundException() {
        super("Resource not found");
    }

    public ResourceNotFoundException(String notAvailable) {
        super(notAvailable);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String value) {
        super(String.format("%s not found with %s: %s", resourceName, fieldName, value));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = value;
    }

}
