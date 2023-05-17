package com.t3h.ecommerce.pojo.dto.checkout;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutDTO {
    private String productName;
    private Long count;
    private Double totalMoneySingle;
}
