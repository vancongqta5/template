package com.t3h.ecommerce.controller;

import com.t3h.ecommerce.dto.request.admin_category.CategoryAddDTO;
import com.t3h.ecommerce.dto.request.admin_category.CategoryAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CategoryController {


    private final CategoryService service;


    @GetMapping("/public/categories")
    public BaseResponse<?> getAllColor(){
        return service.getAllCategory();
    }

    @PostMapping("/admin/categories")
    public BaseResponse<?> findCategory(@Valid @RequestBody CategoryAdminRequest request){
        return service.findCategory(request);
    }

    @DeleteMapping("/admin/category")
    public BaseResponse<?> deleteCategory(@Valid @RequestParam("ids") String ids){
        return service.deleteCategory(ids);
    }

    @PostMapping("/admin/category")
    public BaseResponse<?> addCategory(@Valid @RequestBody CategoryAddDTO request){
        return service.createOrEdit(request);
    }
}
