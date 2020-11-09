package com.schooldevops.practical.simpleboard.controller;

import com.schooldevops.practical.simpleboard.dto.RoleDto;
import com.schooldevops.practical.simpleboard.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping("/api/roles")
@RestController
public class RoleController {

    final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public RoleDto saveRole(@RequestBody RoleDto role) {
        return roleService.saveRole(role);
    }

    @GetMapping("/{roleId}")
    public RoleDto getRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById(roleId);
    }

    @PutMapping("/{roleId}")
    public RoleDto updateRoleById(@PathVariable("roleId") Long roleId, @RequestBody RoleDto roleDto) {
        return roleService.updateRoleById(roleId, roleDto);
    }

    @DeleteMapping("/{roleId}")
    public RoleDto deleteRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.deleteRoleById(roleId);
    }

    @PutMapping("/{roleId}/users")
    public RoleDto bindUsers(@PathVariable("roleId") Long roleId, @RequestBody ArrayList<String> userIds) {
        return roleService.bindUsers(roleId, userIds);
    }
}
