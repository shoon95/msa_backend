package com.sparta.msa_exam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ResponseDto <T>{

    private String status;
    private T data;
    private String message;

    public static <T> ResponseDto<T> success(String message) {
        return new ResponseDto<>("success", null, message);
    }

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>("success", data, message);
    }

    public static <T> ResponseDto<T> error(String message) {
        return new ResponseDto<>("error", null, message);
    }
}
