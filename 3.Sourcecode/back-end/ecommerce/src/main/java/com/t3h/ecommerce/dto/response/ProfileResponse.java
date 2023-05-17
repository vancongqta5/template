package com.t3h.ecommerce.dto.response;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String userName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private Integer gender;
}
