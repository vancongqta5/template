package com.t3h.ecommerce.pojo.dto.product;

import com.t3h.ecommerce.entities.product.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;


@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private Double cost;;
    private String url;
    private Long quantity;
    private String shortDescription;
    private Long createdDate;
    private Long updatedDate;

    public ProductDTO(ProductDB product){
        BeanUtils.copyProperties(product,this);
    }

    public ProductDTO(Product product){
        BeanUtils.copyProperties(product,this);
    }
}
