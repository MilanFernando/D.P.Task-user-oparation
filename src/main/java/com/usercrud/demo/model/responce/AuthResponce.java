package com.usercrud.demo.model.responce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponce {
        private String accessToken;
        private String refreshToken;
        private String message;
    }