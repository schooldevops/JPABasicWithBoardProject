package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.repository.RoleRepository;
import com.schooldevops.practical.simpleboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public List<UserDto> findNameAndPeriodDate(String userName, Integer passedDay) {

        LocalDateTime endDate = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime startDate = LocalDate.now().minusDays(passedDay).atStartOfDay();

        List<User> users = this.userRepository.findAllByNameAndCreatedAtBetween(userName, startDate, endDate);

        return users.stream().map(user -> user.getDTO()).collect(Collectors.toList());
    }

    public List<UserDto> findTop3ByName(String userName) {
        List<User> users = this.userRepository.findTop3ByName(userName);
        return users.stream().map(user -> user.getDTO()).collect(Collectors.toList());
    }

    public List<UserDto> findByNameWithSort(String userName, String ascdes) {
        Sort sort = getUserSortByCreatedAtV2(ascdes);

        List<User> users = this.userRepository.findByName(userName, sort);
        return users.stream().map(user -> user.getDTO()).collect(Collectors.toList());
    }

    private Sort getUserSortByCreatedAt(String ascdes) {
        Sort.TypedSort<User> userSortType = Sort.sort(User.class);
        Sort.TypedSort<LocalDateTime> sortBy = Sort.sort(User.class).by(User::getCreatedAt);
        Sort sort = null;
        if ("DES".equals(ascdes)) {
            sort = sortBy.descending();
        }
        else {
            sort = sortBy.ascending();
        }
        return sort;
    }

    private Sort getUserSortByCreatedAtV2(String ascdes) {
        if ("DES".equals(ascdes)) {
            return Sort.by("createdAt").descending();
        }
        return Sort.by("createdAt").ascending();
    }

    public Page<UserDto> findAllUserWithPaging(int page, int sizePerPage) {
        PageRequest pageReq = PageRequest.of(page, sizePerPage);
        Page<User> usersWithPage = userRepository.findAll(pageReq);

        log.info("Total Users: " + usersWithPage.getTotalElements());
        log.info("Total Pages: " + usersWithPage.getTotalPages());

        List<UserDto> users = usersWithPage.getContent().stream().map(user -> user.getDTO()).collect(Collectors.toList());

        return new PageImpl<>(users, usersWithPage.getPageable(), usersWithPage.getTotalElements());
    }


    public Page<UserDto> findAllUserWithPagingAndSort(int page, int sizePerPage, String ascdes) {
        PageRequest pageReq = PageRequest.of(page, sizePerPage, getUserSortByCreatedAtV2(ascdes));
        Page<User> usersWithPage = userRepository.findAll(pageReq);

        log.info("Total Users: " + usersWithPage.getTotalElements());
        log.info("Total Pages: " + usersWithPage.getTotalPages());

        List<UserDto> users = usersWithPage.getContent().stream().map(user -> user.getDTO()).collect(Collectors.toList());

        return new PageImpl<>(users, usersWithPage.getPageable(), usersWithPage.getTotalElements());
    }
}
