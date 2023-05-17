package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.admin_customer.CustomerAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CustomerController {

    private final CustomerService service;

    @PostMapping("admin/customer")
    public BaseResponse<?> findCustomer(@Valid @RequestBody CustomerAdminRequest request){
        return service.findCustomer(request);
    }

    @GetMapping("admin/customer")
    public BaseResponse<?> changeStatus(@Valid @RequestParam("ids") String ids,
                                        @Valid @RequestParam("status") String status){
        return service.changeStatus(ids, status);
    }

    @PostMapping("admin/customer/export")
    public ResponseEntity<?> exportExcelCustomer(@Valid @RequestBody CustomerAdminRequest request){
        return service.exportExcelCustomer(request);
    }
}
