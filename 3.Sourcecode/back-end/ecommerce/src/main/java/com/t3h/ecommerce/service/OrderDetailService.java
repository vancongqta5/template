package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.PageRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.cart.CarrModelRequest;

public interface OrderDetailService {

    BaseResponse<?> findAllCard(PageRequest request);

    BaseResponse<?> findMoney(String id, String count);

    BaseResponse<?> delete(String id);

    BaseResponse<?> create(String id);
}
