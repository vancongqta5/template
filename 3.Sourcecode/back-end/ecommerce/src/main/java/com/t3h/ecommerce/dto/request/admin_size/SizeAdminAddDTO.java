package com.t3h.ecommerce.dto.request.admin_size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeAdminAddDTO {
    private Long id;
    private String sizeCode;
    private String sizeName;
}
