package com.usercrud.demo.controller;

import com.usercrud.demo.model.request.UserRefreshTokenRequest;
import com.usercrud.demo.model.request.UserRequest;
import com.usercrud.demo.model.responce.AuthResponce;
import com.usercrud.demo.model.responce.BaseResponse;
import com.usercrud.demo.model.responce.DefaultResponce;
import com.usercrud.demo.service.UserService;
import com.usercrud.demo.utils.ResponceUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<DefaultResponce> registerUser(@Valid @RequestBody UserRequest userDto) {
        BaseResponse<?> response = userService.register(userDto);
        log.info("user registered");
        if (response.getCode().equals(ResponceUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(new DefaultResponce(response.getCode(),response.getMessage(),response.getTitle(),response.getData()));
        } else {
            return ResponseEntity.badRequest().body((new DefaultResponce(response.getCode(), response.getTitle(),response.getMessage(),response.getData())));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<DefaultResponce> login(@RequestBody UserRequest userRequest){
        AuthResponce authresponse=userService.verify(userRequest);

        log.info("user login");
        if (authresponse.getRefreshToken() != null) {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.SUCCESS_CODE, authresponse.getMessage(), "SUCCESS",authresponse));
        } else {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.FAIL_CODE, authresponse.getMessage(), "FAILED",authresponse));
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<DefaultResponce> refreshToken(@RequestBody UserRefreshTokenRequest userRefreshTokenRequest) {
        AuthResponce authResponce=userService.refreshToken(userRefreshTokenRequest);

        log.info("user refresh token");
        if (authResponce.getRefreshToken() != null) {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.SUCCESS_CODE, authResponce.getMessage(), "SUCCESS",authResponce));
        } else {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.FAIL_CODE, authResponce.getMessage(), "FAILED",authResponce));
        }
    }
    @GetMapping(path = "/getAllUsers")
    public ResponseEntity<DefaultResponce> getAllUsers() {
        DefaultResponce response = userService.getAllUsers();

        if (response.getCode().equals(ResponceUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.SUCCESS_CODE, response.getTitle(),response.getMessage(), response.getData()));
        }  else {
            return ResponseEntity.badRequest()
                    .body(new DefaultResponce(ResponceUtil.FAIL_CODE,response.getTitle(), response.getMessage(), response.getData()));
        }
    }
    @DeleteMapping(path = "/deleteUser/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
    @GetMapping("/getuserbyid/{id}")
    public ResponseEntity<DefaultResponce> getUserById(@PathVariable int id) {
        DefaultResponce response=userService.getUserById(id);
        if (response.getCode().equals(ResponceUtil.SUCCESS_CODE)) {
            return ResponseEntity.ok(new DefaultResponce(ResponceUtil.SUCCESS_CODE, response.getTitle(),response.getMessage(), response.getData()));
        }  else {
            return ResponseEntity.badRequest()
                    .body(new DefaultResponce(ResponceUtil.FAIL_CODE,response.getTitle(), response.getMessage(), response.getData()));
        }
}}


