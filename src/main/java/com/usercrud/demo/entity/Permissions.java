package com.usercrud.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique=true)
    private String name;
    @ManyToMany(mappedBy = "permissions",fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<Role> roles;




}
