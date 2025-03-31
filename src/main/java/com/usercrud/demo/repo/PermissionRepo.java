package com.usercrud.demo.repo;

import com.usercrud.demo.entity.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permissions,Integer> {
    Optional<Permissions> findByName(String name);
}
