package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.admin_revenue.AdminRevenueReq;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.revenue.RevenueRequestDTO;
import org.springframework.http.ResponseEntity;

public interface RevenueService {
    BaseResponse<?> findRevenue(AdminRevenueReq req);

    ResponseEntity<?> exportRevenue();

}
