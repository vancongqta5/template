package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.PageRequest;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdmin;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminRequest;
import com.t3h.ecommerce.dto.request.shop_product.ShopProductDTO;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/")
public class ShopPageController {


    private final ProductService service;


    @PostMapping("/shop-page")
    public BaseResponse<?> findProductForShop(@Valid @RequestBody ProductAdminRequest pageRequest){
        return service.findProductForShop(pageRequest);
    }

}
