package com.t3h.ecommerce.pojo.dto.product_detail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewAdd {
    private Long userId;
    private Long productId;
    private String comment;
}
