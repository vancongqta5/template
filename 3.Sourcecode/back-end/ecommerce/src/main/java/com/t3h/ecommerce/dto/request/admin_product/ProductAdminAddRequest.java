package com.t3h.ecommerce.dto.request.admin_product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminAddRequest {
    private Long id;
    private String productName;
    private Long quantity;
    private Double cost;
    private String shortDescription;
    private String description;
    private Long categoryId;
    private Long sizeId;
    private Long discountId;
    private Long colorId;
    private List<String> urlImg;

}
