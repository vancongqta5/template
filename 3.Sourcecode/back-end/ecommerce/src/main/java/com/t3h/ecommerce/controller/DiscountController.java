package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_discount.DiscountAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class DiscountController {


    private final DiscountService service;

    @GetMapping("/public/discounts")
    public BaseResponse<?> getAllDiscount(){
        return service.getAllDiscount();
    }


    @PostMapping("/admin/discounts")
    public BaseResponse<?> findDiscount(@Valid @RequestBody DiscountAdminRequest request){
        return service.findDiscount(request);
    }

    @DeleteMapping("/admin/discount")
    public BaseResponse<?> deleteDiscount(@Valid @RequestParam("ids") String ids){
        return service.deleteDiscount(ids);
    }

    @PostMapping("/admin/discount")
    public BaseResponse<?> createOrEdit(@Valid @RequestBody DiscountAdminAddDTO req){
        return service.createOrEdit(req);
    }

}
