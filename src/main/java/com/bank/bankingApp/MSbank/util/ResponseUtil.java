package com.bank.bankingApp.MSbank.util;

import com.bank.bankingApp.MSbank.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> success (String message, T data){
        return ResponseEntity.ok(new ApiResponse<>(true, HttpStatus.OK.value(),message, LocalDateTime.now().toString(),data,null));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created (String message, T data){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, HttpStatus.CREATED.value(),message, LocalDateTime.now().toString(),data,null));
    }
    public static <T> ResponseEntity<ApiResponse<T>> error (HttpStatus status ,String message, T data){
        return ResponseEntity.status(status).body(new ApiResponse<>(true, status.value(),message, LocalDateTime.now().toString(),data,null));
    }

}
