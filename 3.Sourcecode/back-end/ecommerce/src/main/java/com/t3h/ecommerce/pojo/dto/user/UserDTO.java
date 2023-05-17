package com.t3h.ecommerce.pojo.dto.user;

import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.pojo.dto.product.ProductDB;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String phoneNumber;
    private Integer gender;
    private Integer status;
    private String address;

    public UserDTO(User user){
        BeanUtils.copyProperties(user,this);
    }
}
