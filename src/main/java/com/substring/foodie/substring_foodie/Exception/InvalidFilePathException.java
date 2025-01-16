package com.substring.foodie.substring_foodie.Exception;

public class InvalidFilePathException extends RuntimeException{

    public InvalidFilePathException(String message) {
        super(message);
    }

    public InvalidFilePathException() {
        super("Invalid file path");
    }
}
