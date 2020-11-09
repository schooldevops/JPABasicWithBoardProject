package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.RoleDto;
import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.RoleRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public RoleDto saveRole(RoleDto roleDto) {
        RoleEntity entity = roleDto.getEntity();
        RoleEntity savedRole = roleRepository.save(entity);

        return savedRole.getDTO();
    }

    public RoleDto getRoleById(Long roleId) {
        Optional<RoleEntity> roleOption = roleRepository.findById(roleId);

        RoleEntity roleEntity = roleOption.orElseThrow(() -> new RuntimeException("Ther are any role by id " + roleId));

        return roleEntity.getDTO();
    }

    public RoleDto updateRoleById(Long roleId, RoleDto roleDto) {
        Optional<RoleEntity> roleOption = roleRepository.findById(roleId);
        RoleEntity roleEntity = roleOption.orElseThrow(() -> new RuntimeException("Ther are any role by id " + roleId));

        if (roleDto.getName() != null) {
            roleEntity.setName(roleDto.getName());
        }

        RoleEntity savedEntity = roleRepository.save(roleEntity);

        return savedEntity.getDTO();
    }

    public RoleDto deleteRoleById(Long roleId) {
        Optional<RoleEntity> roleOption = roleRepository.findById(roleId);
        RoleEntity roleEntity = roleOption.orElseThrow(() -> new RuntimeException("Ther are any role by id " + roleId));

        roleRepository.deleteById(roleId);

        return roleEntity.getDTO();
    }

    public RoleDto bindUsers(Long roleId, List<String> userIds) {
        Optional<RoleEntity> roleOption = roleRepository.findById(roleId);
        RoleEntity roleEntity = roleOption.orElseThrow(() -> new RuntimeException("Ther are any role by id " + roleId));

        List<User> users = userRepository.findAllByIdIn(userIds);

        if (users == null || users.isEmpty()) {
            throw new RuntimeException("there are any users by user ids: " + userIds);
        }

        for (User user : users) {
            roleEntity.addUser(user);
        }

        RoleEntity savedRole = roleRepository.save(roleEntity);

        return savedRole.getDTO();
    }
}
