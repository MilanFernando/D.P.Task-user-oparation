package com.usercrud.demo.service;

import com.usercrud.demo.entity.Permissions;
import com.usercrud.demo.entity.Role;
import com.usercrud.demo.model.PermissionDto;
import com.usercrud.demo.model.RoleDto;
import com.usercrud.demo.repo.PermissionRepo;
import com.usercrud.demo.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;


    public Role saveRole(RoleDto roleDto) {

        Role role = new Role();
        role.setName(roleDto.getRoleName()); // Set role name
            log.info("set name to role: {}", role);
        List<Permissions> permissions = new ArrayList<>();

        for (PermissionDto permissionDto : roleDto.getPermissions()) {
            Permissions permission = permissionRepo.findByName(permissionDto.getPermissionName()).orElseGet(()->{
                        Permissions newPermission = new Permissions();
                        newPermission.setName(permissionDto.getPermissionName());
                        return permissionRepo.save(newPermission); // Save new permission
                    });

            permissions.add(permission);
        }
        role.setPermissions(permissions); // Assign permissions to the role
        log.info("role.permission"+role);
        return roleRepo.save(role); // Save role with permissions
    }

    public List<Role> getAllRoles() {
        List<Role> roles=roleRepo.findAll();
        log.info("get all roles");
        return roles;
    }

    public Role updateRole(int id,RoleDto roleDto) {
        Role existRole = roleRepo.findById(id);
        if (existRole == null) {
            throw new RuntimeException("Role not found");
        }

        existRole.setName(roleDto.getRoleName());

        List<Permissions> permissions = new ArrayList<>();

        for (PermissionDto permissionDto : roleDto.getPermissions()) {

            Permissions permission = permissionRepo.findByName(permissionDto.getPermissionName())
                    .orElseGet(() -> {
                        Permissions newPermission = new Permissions();
                        newPermission.setName(permissionDto.getPermissionName());
                        return permissionRepo.save(newPermission);
                    });

            permissions.add(permission);
        }

        existRole.setPermissions(permissions);
        // Assign updated permissions
        log.info("updating....");
        return roleRepo.save(existRole);// Save updated role
    }

    public void deleteRole(int id) {
        roleRepo.deleteById(id);
        log.info("Role deleted successfully");
    }
}
