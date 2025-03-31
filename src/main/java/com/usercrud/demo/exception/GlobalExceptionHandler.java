package com.usercrud.demo.exception;

import com.usercrud.demo.model.responce.DefaultResponce;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request){

        Map<String, String> fieldError = new HashMap<>();
        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            fieldError.put(error.getField(), error.getDefaultMessage());
        }

        DefaultResponce thirdPartyDefaultResponse = DefaultResponce.builder()
                .code("500")
                .title("Failed")
                .message("Input validation error")
                .data(fieldError)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(thirdPartyDefaultResponse);
    }
}
