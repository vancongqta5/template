package com.t3h.ecommerce.pojo.dto.product;

import lombok.Data;

public interface ProductDB {
    Integer getId();
    String getProductName();
    Double getCost();;
    String getUrl();
}
