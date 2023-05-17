package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.admin_product.ProductAdminAddRequest;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductReviewReq;
import com.t3h.ecommerce.service.ProductDetailService;
import com.t3h.ecommerce.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/")
public class ProductController {

    private final ProductService service;

    private final ProductDetailService productDetailService;


    @PostMapping("admin/product")
    BaseResponse<?> findProduct(@Valid @RequestBody ProductAdminRequest request){
        return service.findProduct(request);
    }

    @PostMapping("admin/add-product")
    BaseResponse<?> createOrEditProduct(@Valid @RequestBody ProductAdminAddRequest request){
        return service.createOrEditProduct(request);
    }

    @GetMapping("admin/product")
    BaseResponse<?> detailProduct(@Valid @RequestParam("id") String id){
        return service.findProductById(id);
    }


    @DeleteMapping("admin/product")
    BaseResponse<?> deleteProduct( @Valid @RequestParam("Ids") String Ids){
        return service.deleteProduct(Ids);
    }

    @PostMapping("admin/reviews")
    BaseResponse<?> findReviews(@Valid @RequestBody ProductReviewReq req){
        return productDetailService.findReview(req);
    }

    @GetMapping("admin/product/export")
    public ResponseEntity<Resource> exportProduct() {
        return service.exportExcelProduct();
    }

    @PostMapping("admin/product/import")
    public BaseResponse<?> importExcel( @Valid @RequestParam("file") MultipartFile file){
        return service.importExcelProduct(file);
    }
}
