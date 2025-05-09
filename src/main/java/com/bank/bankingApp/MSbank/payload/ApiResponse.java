package com.bank.bankingApp.MSbank.payload;


import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T>{

    private boolean success;
    private int status;
    private String message;
    private String timestamp;
    private T data;
    private String errorCode;

}
