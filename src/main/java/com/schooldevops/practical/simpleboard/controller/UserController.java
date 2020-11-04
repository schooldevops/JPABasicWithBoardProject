package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.dto.UserDetailDto;
import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.UserDetail;
import com.schooldevops.practical.simpleboard.services.UserDetailService;
import com.schooldevops.practical.simpleboard.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/users")
@RestController
public class UserController {

    final UserService userService;
    final UserDetailService userDetailService;

    @Autowired
    public UserController(UserService userService, UserDetailService userDetailService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
    }

    @PostMapping
    public UserDto saveUser(@RequestBody UserDto user) {
        return userService.saveUser(user);
    }


    @PostMapping("/detail")
    public UserDetailDto saveUser(@RequestBody UserDetailDto userDetail) {
        return userDetailService.saveUserDetail(userDetail);
    }

    @GetMapping("/{userId}")
    public UserDto findByUserId(@PathVariable("userId") String userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping("/{userId}/detail")
    public UserDetailDto findUserDetailByUserId(@PathVariable("userId") String userId) {
        return userDetailService.findByUserId(userId);
    }

    @PutMapping("/{userId}")
    public UserDto updateByUserId(@PathVariable("userId") String userId, @RequestBody UserDto user) {
        if (user == null) { throw new IllegalArgumentException(); }
        return userService.updateByUserId(userId, user);
    }

    @PutMapping("/{userId}/detail")
    public UserDetailDto updateByUserId(@PathVariable("userId") String userId, @RequestBody UserDetail userDetail) {
        if (userDetail == null) { throw new IllegalArgumentException(); }
        return userDetailService.updateByUserId(userId, userDetail);
    }

    @DeleteMapping("/{userId}/detail")
    public UserDetailDto deleteUserDetailByUserId(@PathVariable("userId") String userId) {
        return userDetailService.deleteByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteByUserId(@PathVariable("userId") String userId) {
        return userService.deleteByUserId(userId);
    }
}
