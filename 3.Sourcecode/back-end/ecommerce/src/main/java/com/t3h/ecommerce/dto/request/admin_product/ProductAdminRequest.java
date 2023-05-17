package com.t3h.ecommerce.dto.request.admin_product;

import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.Data;

@Data
public class ProductAdminRequest {
    private FilterDate filterDate;
    private PageRequest pageRequest;
    private String productName;
    private Long quantity;
    private Double cost;
    private Long categoryId;

}
