package com.schooldevops.practical.simpleboard.repository;

import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findAllByIdIn(List<Long> roleIds);
}
