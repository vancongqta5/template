package com.t3h.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String avatar;
    private Integer gender;
    private String phoneNumber;
    private String address;
}
