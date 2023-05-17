package com.t3h.ecommerce.dto.request.shop_product;

public interface ShopProduct {
    Long getId();
    String getProductName();
    Double getCost();
    String getImgAvt();
    String getDiscountName();
    String getCategoryName();
    String getShortDescription();
    Long getCategoryId();
    Long getDiscountId();


}
