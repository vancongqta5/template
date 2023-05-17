package com.t3h.ecommerce.dto.request.admin_customer;

import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAdminRequest {
    private PageRequest pageRequest;
    private String textSearch;
    private String phoneNumber;
    private Integer status;
}
