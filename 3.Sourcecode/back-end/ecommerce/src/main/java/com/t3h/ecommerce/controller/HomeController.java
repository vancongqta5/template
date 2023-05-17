package com.t3h.ecommerce.controller;

import com.t3h.ecommerce.dto.request.PageRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
//@Api("moduls shop api")
@RequestMapping("/api/")
public class HomeController {

    @Autowired
    private final ProductService productService;

    @PostMapping("public/home")
    BaseResponse<?> getProductForHome(@Valid @RequestBody PageRequest pageRequest){
        return productService.findProductForHome(pageRequest);
    }


}
