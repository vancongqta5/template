package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.admin_customer.CustomerAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

    BaseResponse<?> findCustomer(CustomerAdminRequest request);

    BaseResponse<?> changeStatus(String ids, String status);

    ResponseEntity<?> exportExcelCustomer(CustomerAdminRequest request);
}
