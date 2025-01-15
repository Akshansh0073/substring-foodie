package com.substring.foodie.substring_foodie.Controller;

import com.substring.foodie.substring_foodie.Exception.ResourceNotFoundException;
import com.substring.foodie.substring_foodie.config.AppConstants;
import com.substring.foodie.substring_foodie.dto.UserDto;
import com.substring.foodie.substring_foodie.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> create (@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<UserDto>(userService.saveUser(userDto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(value="page", required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(value="size", required = false, defaultValue = AppConstants.PAGE_SIZE) int size,
            @RequestParam(value="sortBy", required = false, defaultValue = "createdDate") String sortBy,
            @RequestParam(value="sortOrder", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir
    )
    {
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto userDto, @PathVariable String userId) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser (@PathVariable String userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<String>("SuccessFully deleted", HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById (@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail (@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<UserDto>> getUserByName (@PathVariable String name) {
        return ResponseEntity.ok(userService.getUserByName(name));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Page<UserDto>> getUserByKeywordInName (
            @PathVariable String keyword,
            @RequestParam(value="page", required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(value="size", required = false, defaultValue = AppConstants.PAGE_SIZE) int size,
            @RequestParam(value="sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value="sortOrder", required = false, defaultValue = AppConstants.SORT_DIR) String sortDir
    )
    {
        Sort sort  = sortDir.equalsIgnoreCase("asc")?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);
        return ResponseEntity.ok(userService.searchUserName(keyword, pageable));
    }



}
