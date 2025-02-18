package com.substring.foodie.substring_foodie.Exception;

import com.substring.foodie.substring_foodie.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse messageOb = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<ErrorResponse>(messageOb, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFilePathException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFilePathException(InvalidFilePathException ex){
        ErrorResponse messageOb = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<ErrorResponse>(messageOb, HttpStatus.NOT_FOUND);
    }


    // Important to understand all exceptions with custom message
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();

        //fetch all errors list from BindingResult
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        //iterate all errors and put the error to map
        allErrors.forEach(error -> {
            //error: we have to fetch the field
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errorMap.put(fieldName, message);
        });

        logger.info(errorMap.toString());
        return errorMap;
    }
}
