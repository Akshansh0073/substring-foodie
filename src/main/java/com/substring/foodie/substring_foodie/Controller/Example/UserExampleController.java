package com.substring.foodie.substring_foodie.Controller.Example;

import com.substring.foodie.substring_foodie.example.entity.UserDtoExample;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class UserExampleController {

    private Logger logger = LoggerFactory.getLogger(UserExampleController.class);

    // Valid is important to validate the data
    @PostMapping("/signup")
    public String signup(
            @Valid @RequestBody UserDtoExample userDTO,
            @RequestHeader("User-Agent") String agent,
            @RequestHeader("Authorization") String token
    ) {

        //print on console:

        //more control: Logging framework: spring boot: [inbuilt]
//            -> console
//             -> file
//              -> change printing form
//              -> error
//                -> debug
//              -> trace
//             -> info
//        System.out.println(userData.getName());

        logger.info("user name : {}", userDTO.getName());
        logger.info("password : {} ", userDTO.getPassword());
        logger.info("email : {} ", userDTO.getEmail());
        logger.info("age : {} ", userDTO.getAge());
        logger.info("user agent : {}", agent);
        logger.info("token : {}", token);
        return "we got data";

    }

}
