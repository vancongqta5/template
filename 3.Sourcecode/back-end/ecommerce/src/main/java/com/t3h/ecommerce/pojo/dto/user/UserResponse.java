package com.t3h.ecommerce.pojo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private List<UserDTO> data;
    private String message;
    private Integer stutus;
}
