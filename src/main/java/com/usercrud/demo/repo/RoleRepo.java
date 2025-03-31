package com.usercrud.demo.repo;

import com.usercrud.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepo extends JpaRepository<Role,Integer> {
        Role findById(int id);
}
