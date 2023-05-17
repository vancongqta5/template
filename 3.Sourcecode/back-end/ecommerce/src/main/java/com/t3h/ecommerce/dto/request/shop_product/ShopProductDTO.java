package com.t3h.ecommerce.dto.request.shop_product;

import com.t3h.ecommerce.dto.request.admin_product.ProductAdmin;
import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.entities.product.Discount;
import com.t3h.ecommerce.entities.product.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ShopProductDTO {

    private  long id;
    private String productName;
    private Double cost;
    private String imgAvt;
    private String discountName;
    private String categoryName;
    private String shortDescription;
    private Long categoryId;
    private Long discountId;

    public ShopProductDTO(ShopProduct product){
        BeanUtils.copyProperties(product,this);
    }

}
