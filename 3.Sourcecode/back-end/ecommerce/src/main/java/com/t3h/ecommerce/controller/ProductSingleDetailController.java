package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductReviewAdd;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductReviewReq;
import com.t3h.ecommerce.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop/")
public class ProductSingleDetailController {

    private final ProductDetailService service;

    @GetMapping("product-detail")
    public BaseResponse<?> findProductDetail(@Valid @RequestParam("id") String id){
        return service.findProductDetail(id);
    }

    @PostMapping("product-detail/review")
    public BaseResponse<?> findProductReview(@Valid @RequestBody ProductReviewReq req){
        return service.findReview(req);
    }

    @PostMapping("product-detail/review/add")
    public BaseResponse<?> createProductReview(@Valid @RequestBody ProductReviewAdd req){
        return service.createReview(req);
    }
}
