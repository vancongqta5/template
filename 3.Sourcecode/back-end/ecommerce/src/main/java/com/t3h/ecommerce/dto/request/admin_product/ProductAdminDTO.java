package com.t3h.ecommerce.dto.request.admin_product;

import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.entities.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAdminDTO {
    private Long id;
    private String productName;
    private String shortDescription;
    private Double cost;
    private Long quantity;
    private Long categoryId;
    private Long colorId;
    private Long discountId;
    private Long sizeId;
    private Long createdDate;
    private Long updatedDate;

    public ProductAdminDTO(ProductAdmin product){
        BeanUtils.copyProperties(product,this);
    }
}
