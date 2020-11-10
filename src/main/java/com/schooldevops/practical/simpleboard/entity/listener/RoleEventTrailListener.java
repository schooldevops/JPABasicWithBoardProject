package com.schooldevops.practical.simpleboard.entity.listener;

import com.schooldevops.practical.simpleboard.entity.RoleEntity;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
public class RoleEventTrailListener {

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(RoleEntity role) {
        if (role.getId() == null) {
            log.info("Brand New Role Insert Event. ");
        } else {
            log.info("Update or delete Role: " + role.getId());
        }
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(RoleEntity role) {
        log.info("after add/update/delete complete for role: " + role.getId());
    }

    @PostLoad
    private void afterLoad(RoleEntity role) {
        log.info("role loaded from database: " + role.getId());
    }
}
