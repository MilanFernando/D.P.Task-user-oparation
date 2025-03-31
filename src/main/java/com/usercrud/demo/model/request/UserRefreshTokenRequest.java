package com.usercrud.demo.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRefreshTokenRequest {
    private String refreshToken;
}
