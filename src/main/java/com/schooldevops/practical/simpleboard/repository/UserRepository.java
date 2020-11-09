package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByIdIn(List<String> userIds);
}
