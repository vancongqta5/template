package com.t3h.ecommerce.pojo.dto.cart;

public interface CartDB {
     Long getProductId();
     String getProductName();
     String getImgAvt();
     Double getCost();
     Long getInventory();
     Long getQuantity();
}
