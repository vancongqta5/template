package com.t3h.ecommerce.pojo.dto.product_detail;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewDTO {
    private String fullName;
    private String urlAvt;
    private Float rating;
    private String comment;
    private Long createdDate;
    private Long updatedDate;
}
