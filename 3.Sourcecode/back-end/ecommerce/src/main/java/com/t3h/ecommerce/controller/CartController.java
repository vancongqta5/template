package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.PageRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/shop/cart")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CartController {

    private final OrderDetailService service;


    @PostMapping("/products")
    public BaseResponse<?> findCart(@Valid @RequestBody PageRequest request){
        return service.findAllCard(request);
    }


    @GetMapping("/product-detail")
    public BaseResponse<?> findMoney(@Valid @RequestParam("id") String id,
                                     @Param("count") String count){
        return service.findMoney(id, count) ;
    }

    @DeleteMapping("/delete")
    public BaseResponse<?> delete(@Valid @RequestParam("id") String id){
        return service.delete(id);
    }

    @GetMapping("/create")
    public BaseResponse<?> create(@Valid @RequestParam("id") String id){
        return service.create(id);
    }
}
