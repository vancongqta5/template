package com.t3h.ecommerce.pojo.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long productId;
    private String productName;
    private String imgAvt;
    private Double cost;
    private Long inventory;
    private Long quantity;

    public CartDTO(CartDB cartDB){
        BeanUtils.copyProperties(cartDB, this);
    }

}
