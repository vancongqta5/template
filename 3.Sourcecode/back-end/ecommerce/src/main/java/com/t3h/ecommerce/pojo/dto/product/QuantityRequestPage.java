package com.t3h.ecommerce.pojo.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityRequestPage {
    private Long minQuantity;
    private Long maxQuantity;
}
