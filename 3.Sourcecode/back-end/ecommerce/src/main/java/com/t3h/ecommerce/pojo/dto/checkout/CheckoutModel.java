package com.t3h.ecommerce.pojo.dto.checkout;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutModel {
    private Long productId;
    private Long count;
}
