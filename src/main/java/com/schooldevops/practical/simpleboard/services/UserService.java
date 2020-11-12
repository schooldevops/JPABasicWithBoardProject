package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.RoleRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @PersistenceContext
    EntityManager em;

    final UserRepository userRepository;
    final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserDto saveUserUsingEM(UserDto user) {
        User userEntity = user.getEntity();

        this.em.persist(userEntity);
        userEntity.setName("Modified Name:");

//        this.em.detach(userEntity);
//        this.em.flush();

        return userEntity.getDTO();
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

    public UserDto bindRolesToUser(String userId, List<Long> roleIds) {
        Optional<User> userIds = this.userRepository.findById(userId);
        User findUser = userIds.orElseThrow(() -> new RuntimeException("There are any User by userId for Bind roles."));
        List<RoleEntity> roles = roleRepository.findAllByIdIn(roleIds);

        if (roles == null || roles.isEmpty()) {
            throw new RuntimeException("There are any Role by roleId. " + roleIds);
        }

        roles.forEach(role -> {
            findUser.addRole(role);
        });

        User savedUser = userRepository.save(findUser);

        return savedUser.getDTO();
    }
}
