package com.t3h.ecommerce.service;


import com.t3h.ecommerce.dto.request.admin_category.CategoryAddDTO;
import com.t3h.ecommerce.dto.request.admin_category.CategoryAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;

public interface CategoryService {

    BaseResponse<?> getAllCategory();

    BaseResponse<?> findCategory(CategoryAdminRequest request);

    BaseResponse<?> deleteCategory(String ids);

    BaseResponse<?> createOrEdit(CategoryAddDTO request);
}
