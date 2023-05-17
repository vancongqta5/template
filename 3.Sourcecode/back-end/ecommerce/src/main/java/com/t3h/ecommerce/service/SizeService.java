package com.t3h.ecommerce.service;


import com.t3h.ecommerce.dto.request.admin_size.SizeAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_size.SizeAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;

public interface SizeService {

    BaseResponse<?> getAllSize();

    BaseResponse<?> findSize(SizeAdminRequest request);

    BaseResponse<?> deleteSize(String ids);

    BaseResponse<?> createOrEdit(SizeAdminAddDTO request);
}
