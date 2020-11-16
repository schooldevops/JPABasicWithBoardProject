package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    List<User> findAllByIdIn(List<String> userIds);

    List<User> findAllByNameAndCreatedAtBetween(String name, LocalDateTime startDate, LocalDateTime endDate);

    List<User> findTop3ByName(String name);

    List<User> findByName(String name, Sort sort);

}
