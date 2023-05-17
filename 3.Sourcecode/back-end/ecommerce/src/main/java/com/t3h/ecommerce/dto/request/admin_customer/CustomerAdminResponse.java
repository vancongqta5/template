package com.t3h.ecommerce.dto.request.admin_customer;


import com.t3h.ecommerce.entities.core.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAdminResponse {
    private Long id;
    private String fullName;
    private String userName;
    private Integer status;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer gender;
    private Long createdDate;
    private Long updatedDate;

    public CustomerAdminResponse(CustomerAdmin user){
        BeanUtils.copyProperties(user, this);
    }
}
