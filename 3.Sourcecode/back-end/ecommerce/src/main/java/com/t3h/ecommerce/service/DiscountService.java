package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;

public interface DiscountService {

    BaseResponse<?> getAllDiscount();

    BaseResponse<?> findDiscount(DiscountAdminRequest request);

    BaseResponse<?> deleteDiscount(String ids);

    BaseResponse<?> createOrEdit(DiscountAdminAddDTO req);
}
