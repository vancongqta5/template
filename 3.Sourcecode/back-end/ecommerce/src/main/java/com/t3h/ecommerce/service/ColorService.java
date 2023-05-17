package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.admin_color.ColorAdminAdd;
import com.t3h.ecommerce.dto.request.admin_color.ColorAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;

public interface ColorService {

    BaseResponse<?> getAllColor();

    BaseResponse<?> findColor(ColorAdminRequest request);

    BaseResponse<?> deleteColor(String ids);

    BaseResponse<?> createOrEdit(ColorAdminAdd request);
}
