package com.t3h.ecommerce.dto.request.admin_discount;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAdminAddDTO {
    private Long id;
    private String discountName;
    private Float discountPercent;
}
