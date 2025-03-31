package com.usercrud.demo.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotEmpty(message = "user name is required")
    private String username;
    @NotEmpty(message = "password is required")
    private String password;
}
