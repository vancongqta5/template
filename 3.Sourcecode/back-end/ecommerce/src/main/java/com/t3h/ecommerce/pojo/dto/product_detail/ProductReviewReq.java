package com.t3h.ecommerce.pojo.dto.product_detail;


import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewReq {
    private PageRequest pageRequest;
    private Long id;
}
