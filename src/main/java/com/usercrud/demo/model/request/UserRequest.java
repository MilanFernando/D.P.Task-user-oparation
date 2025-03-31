package com.usercrud.demo.model.request;

import com.usercrud.demo.anotation.ValidateNic;
import com.usercrud.demo.anotation.ValidatePhone;
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
    @ValidatePhone(message="Phone number is required")
    private String phoneNumber;
    @ValidateNic(message = "Nic is required")
    private String nic;

}
