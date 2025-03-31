package com.usercrud.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String nic;


    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);

    }
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns =@JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    @Builder.Default
    private List<Role> roles=new ArrayList<>();















































}
