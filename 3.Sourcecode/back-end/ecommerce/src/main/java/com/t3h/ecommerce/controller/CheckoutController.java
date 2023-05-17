package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.checkout.CheckoutRequest;
import com.t3h.ecommerce.pojo.dto.revenue.RevenueRequestDTO;
import com.t3h.ecommerce.service.CheckOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/checkout/")
public class CheckoutController {

    private final CheckOutService service;

    @PostMapping("/products")
    BaseResponse<?> findAllCheckOut(@Valid @RequestBody CheckoutRequest request){
        return  service.getAllProduct(request);
     }


     @PostMapping("/revenue")
     BaseResponse<?> createRevenue(@Valid @RequestBody RevenueRequestDTO requestDTO){
        return service.addRevenue(requestDTO);
     }
}
