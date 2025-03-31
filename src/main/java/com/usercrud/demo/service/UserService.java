package com.usercrud.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.usercrud.demo.entity.Users;
import com.usercrud.demo.model.request.UserRefreshTokenRequest;
import com.usercrud.demo.model.request.UserRequest;
import com.usercrud.demo.model.responce.AuthResponce;
import com.usercrud.demo.model.responce.BaseResponse;
import com.usercrud.demo.model.responce.DefaultResponce;
import com.usercrud.demo.repo.UserRepo;
import com.usercrud.demo.utils.ResponceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final  UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtService;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    @Value("${jwt.secret}")
    private String secretKey;


    public BaseResponse<?> register(UserRequest userRequest){

        String logPrefix = "register -> ";

        log.info(logPrefix+" "+userRequest.toString());
        try{
            log.info("register-> saving...");
            Users user = new Users();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());

            userRepo.save(user);
            log.info("register -> saved"+ user);

            return BaseResponse.builder()
                    .code("0000")
                    .title("SUCCESS")
                    .message("register success")
                    .build();

        }catch (Exception e){
            log.error("register -> Exception : "+e.getMessage());
            e.printStackTrace();
            return BaseResponse.builder()
                    .code("400")
                    .title("Failed")
                    .message("register failed")
                    .build();
        }

    }

    public AuthResponce verify(UserRequest userDto) {
        log.info("send response to verify : " + userDto.getUsername());

        try {
            Users storedUser = userRepo.findByUsername(userDto.getUsername());

            if (storedUser == null) {
                return new AuthResponce(null, null, "Credential invalid");
            }

            if (encoder.matches(userDto.getPassword(), storedUser.getPassword())) {
                log.info("Password match successful");
                String accessToken = jwtService.generateToken(storedUser.getUsername());
                String refreshToken = jwtService.generateRefreshToken(storedUser.getUsername()); // Generate refresh token

                return new AuthResponce(accessToken, refreshToken, "Login successful");
            } else {
                log.error("Wrong password for user: " + userDto.getUsername());
                return new AuthResponce(null, null, "Wrong password");
            }
        } catch (Exception e) {
            log.error("Authentication failed: " + e.getMessage());
            return new AuthResponce(null, null, "Failed");
        }

    }

    public AuthResponce refreshToken(UserRefreshTokenRequest userRefreshTokenRequest) {
        try {
            String refreshToken=userRefreshTokenRequest.getRefreshToken();
            log.info("Refresh token: "+refreshToken);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
                    .build()
                    .verify(refreshToken);

            String username = decodedJWT.getSubject();
            log.info("Token validated successfully. Username extracted: {}", username);

            if (!username.isEmpty()) {
                log.info("user name is not empty");

                String newaccesstoken =jwtUtil.generateToken(username);
                String newrefreshtokrn = jwtUtil.generateRefreshToken(username);
                log.info("genarated access and refresh token");

                return new AuthResponce(newaccesstoken,newrefreshtokrn,"new tokens");
            }else {
                log.info("user not exist:{}", username);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public DefaultResponce getAllUsers() {
        List<Users> usersList=userRepo.findAll();
        return new DefaultResponce(ResponceUtil.SUCCESS_CODE,"success","list by all users",usersList);
    }

    public void deleteUser(int id) {

         userRepo.deleteById(id);
    }

    public DefaultResponce getUserById(int id) {
        Users user=userRepo.getUserById(id);
        if(user != null){
            try{
                user.setPassword(user.getPassword());
            } catch (Exception e) {
                throw new RuntimeException("decryption failed"+e);
            }
        }
        return new DefaultResponce(ResponceUtil.SUCCESS_CODE,"success","get user by id",user);
    }
}







