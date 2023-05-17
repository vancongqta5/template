package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.checkout.CheckoutRequest;
import com.t3h.ecommerce.pojo.dto.revenue.RevenueRequestDTO;


public interface CheckOutService {
    BaseResponse<? > getAllProduct(CheckoutRequest request);

    BaseResponse<?> addRevenue(RevenueRequestDTO requestDTO);
}
