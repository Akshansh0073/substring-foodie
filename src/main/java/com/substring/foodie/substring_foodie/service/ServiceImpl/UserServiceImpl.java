package com.substring.foodie.substring_foodie.service.ServiceImpl;

import com.substring.foodie.substring_foodie.Exception.ResourceNotFoundException;
import com.substring.foodie.substring_foodie.dto.UserDto;
import com.substring.foodie.substring_foodie.entity.User;
import com.substring.foodie.substring_foodie.repository.UserRepo;
import com.substring.foodie.substring_foodie.service.UserService;
import com.substring.foodie.substring_foodie.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepo userRepo;
    private ResourcePatternResolver resourcePatternResolver;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepo userRepo, ResourcePatternResolver resourcePatternResolver) {
        this.userRepo = userRepo;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        userDto.setId(Helper.generateRandomId());
        logger.info("{}", userDto.getEmail());
        User user = convertUserDtoToUser(userDto);
        User savedUser = userRepo.save(user);
        return convertUserToUserDto(savedUser);
    }

    @Override
    public List<UserDto> saveUserList(List<UserDto> userDto) {

        List<UserDto> collect = userDto.stream().peek(user -> user.setId(UUID.randomUUID().toString())).toList();

        List<User> collect1 = collect.stream().map(dto -> convertUserDtoToUser(dto)).
                collect(Collectors.toList());

        List<User> users = userRepo.saveAll(collect1);

        return users.stream().map(user->convertUserToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User" ,"userId", userId));
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());
        user.setName(userDto.getName());
        User savedUser = userRepo.save(user);
        return convertUserToUserDto(savedUser);
    }

    @Override
    public Page<UserDto> getAll(Pageable pageable) {
        Page<User> userPage = userRepo.findAll(pageable);
        return userPage.map(user -> convertUserToUserDto(user));
    }

    @Override
    public List<UserDto> getUserByName(String userName) {
        List<User> userList = userRepo.findByName(userName)
                .stream()
                .findAny() // If you expect multiple users, you may choose findFirst() or another logic
                .map(List::of) // Wrap in a List to keep method signature consistent
                .orElseThrow(()->new ResourceNotFoundException("User","Name keyword",userName));
        return userList.stream().map(user -> convertUserToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User","email",email));
        return convertUserToUserDto(user);
    }

    @Override
    public UserDto getUserById(String userId) {

        User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User" ,"userId", userId));
        return convertUserToUserDto(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public Page<UserDto> searchUserName(String keyword, Pageable pageable) {
        Page<User> byNameContaining = userRepo.findByNameContaining(keyword, pageable);
        return byNameContaining.map(this::convertUserToUserDto);
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAddress(userDto.getAddress());

        return user;
    }

    private UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAddress(user.getAddress());

        return userDto;
    }
}
