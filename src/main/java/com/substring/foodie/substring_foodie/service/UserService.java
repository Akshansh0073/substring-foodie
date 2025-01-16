package com.substring.foodie.substring_foodie.service;

import com.substring.foodie.substring_foodie.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {

    UserDto saveUser(UserDto userDto);

    List<UserDto> saveUserList(List<UserDto> userDto);

    UserDto updateUser(UserDto userDto, String userId);

    Page<UserDto> getAll(Pageable pageable);

    UserDto getUserById(String userId);

    void deleteUser(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> getUserByName(String userName);

    //extra operations
    Page<UserDto> searchUserName(String keyword, Pageable pageable);
}
