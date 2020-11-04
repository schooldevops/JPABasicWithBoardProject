package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, String> {
}
