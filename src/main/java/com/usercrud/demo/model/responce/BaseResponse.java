package com.usercrud.demo.model.responce;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {
    private String code ;
    private String title;
    private String message;
    private T data;
}
