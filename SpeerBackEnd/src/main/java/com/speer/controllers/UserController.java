package com.speer.controllers;

import com.speer.dtos.CreateUserDto;
import com.speer.dtos.LoginUserDto;
import com.speer.entities.User;
import com.speer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    private static String token = "";

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        User user = userService.createUser(createUserDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto loginUserDto) {
        token = userService.loginUser(loginUserDto);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
