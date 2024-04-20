package com.tweeter.User.Service.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String loginId;
    private String password;
    private String contactNumber;
}
