package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.UserDetailDto;
import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.entity.UserDetail;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto saveUser(UserDto user) {

        User userEntity = user.getEntity();

        User save = this.userRepository.save(userEntity);

        return save.getDTO();
    }

    public UserDto findByUserId(String userId) {
        Optional<User> userIds = this.userRepository.findById(userId);
        User user = userIds.orElseThrow(() -> new RuntimeException("There are any User by userId"));

        return user.getDTO();
    }

    public UserDto updateByUserId(String userId, UserDto user) {
        Optional<User> userIds = this.userRepository.findById(userId);
        User findUser = userIds.orElseThrow(() -> new RuntimeException("There are any User by userId for Update."));

        if (user.getName() != null) {
            findUser.setName(user.getName());
        }

        if (user.getBirth() != null) {
            findUser.setBirth(user.getBirth());
        }

        User savedUser = this.userRepository.save(findUser);

        return savedUser.getDTO();

    }

    public UserDto deleteByUserId(String userId) {
        Optional<User> userIds = this.userRepository.findById(userId);
        User findUser = userIds.orElseThrow(() -> new RuntimeException("There are any User by userId for Update."));

        userRepository.delete(findUser);

        return findUser.getDTO();
    }
}
