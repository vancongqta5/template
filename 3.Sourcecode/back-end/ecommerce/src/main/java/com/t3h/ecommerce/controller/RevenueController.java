package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.admin_revenue.AdminRevenueReq;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.RevenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class RevenueController {

    private final RevenueService service;


    @PostMapping("/revenues")
    public BaseResponse<?> findRevenue(@Valid @RequestBody AdminRevenueReq req){
        return service.findRevenue(req);
    }

    @GetMapping("/export/revenue")
    public ResponseEntity<?> exportExcelCustomer(){
        return service.exportRevenue();
    }

}
