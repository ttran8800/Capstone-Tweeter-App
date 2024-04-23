package com.tweeter.User.Service.payload;

import com.tweeter.User.Service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterResponse {
    private boolean error;
    private String errorType;
    private String message;
}