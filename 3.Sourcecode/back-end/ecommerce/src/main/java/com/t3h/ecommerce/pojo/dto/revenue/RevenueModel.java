package com.t3h.ecommerce.pojo.dto.revenue;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueModel {
    private Long productId;
    private Long count;
}
