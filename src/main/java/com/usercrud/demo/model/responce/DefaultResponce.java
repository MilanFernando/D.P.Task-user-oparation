package com.usercrud.demo.model.responce;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponce {
    private String code;
    private String title;
    private String message;
    private Object data;

}
