package com.schooldevops.practical.simpleboard.services;

import com.schooldevops.practical.simpleboard.dto.UserDetailDto;
import com.schooldevops.practical.simpleboard.dto.UserDto;
import com.schooldevops.practical.simpleboard.entity.User;
import com.schooldevops.practical.simpleboard.entity.UserDetail;
import com.schooldevops.practical.simpleboard.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService {

    final UserDetailRepository userDetailRepository;

    @Autowired
    public UserDetailService(UserDetailRepository userDetailRepository) {
        this.userDetailRepository = userDetailRepository;
    }

    public UserDetailDto findByUserId(String userId) {
        Optional<UserDetail> userIds = this.userDetailRepository.findById(userId);
        UserDetail userDetail = userIds.orElseThrow(() -> new RuntimeException("There are any UserDetails by userId"));

        return userDetail.getDTO();
    }

    public UserDetailDto updateByUserId(String userId, UserDetail userDetail) {
        Optional<UserDetail> userIds = this.userDetailRepository.findById(userId);
        UserDetail findUserDetail = userIds.orElseThrow(() -> new RuntimeException("There are any UserDetails by userId for Update"));

        if (userDetail.getCategory() != null) {
            findUserDetail.setCategory(userDetail.getCategory());
        }

        if (userDetail.getAvatarImg() != null) {
            findUserDetail.setAvatarImg(userDetail.getAvatarImg());
        }

        if (userDetail.getNick() != null) {
            findUserDetail.setNick(userDetail.getNick());
        }

//        if (userDetail.getRole() != null) {
//            findUserDetail.setRole(userDetail.getRole());
//        }

        UserDetail savedUserDetail = userDetailRepository.save(findUserDetail);

        return savedUserDetail.getDTO();
    }

    public UserDetailDto deleteByUserId(String userId) {
        Optional<UserDetail> userIds = this.userDetailRepository.findById(userId);
        UserDetail userDetail = userIds.orElseThrow(() -> new RuntimeException("There are any UserDetails by userId for Delete"));

        userDetailRepository.deleteById(userId);

        return userDetail.getDTO();
    }

    public UserDetailDto saveUserDetail(UserDetailDto userDetail) {
        UserDetail entity = userDetail.getEntity();
        UserDetail savedUserDetail = userDetailRepository.save(entity);
        return savedUserDetail.getDTO();
    }
}
