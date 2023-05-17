package com.t3h.ecommerce.pojo.dto.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostRequestPage {
    private Double minCost;
    private Double maxCost;
}
