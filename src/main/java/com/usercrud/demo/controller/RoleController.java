package com.usercrud.demo.controller;

import com.usercrud.demo.entity.Role;
import com.usercrud.demo.model.RoleDto;
import com.usercrud.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/saverole")
    public Role createRole(@RequestBody RoleDto roleDto) {
        log.info("request recieved:"+roleDto.toString());
        return roleService.saveRole(roleDto);

    }
    @GetMapping("/getallroles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }
    @PutMapping("/updaterole/{id}")
    public Role updateRole(@PathVariable int id,@RequestBody RoleDto roleDto) {
        return roleService.updateRole(id ,roleDto);
    }
    @DeleteMapping("/deleterole/{id}")
    public String deleteRole(@PathVariable int id) {
        roleService.deleteRole(id);
        return "succesfully deleted";
    }
}
