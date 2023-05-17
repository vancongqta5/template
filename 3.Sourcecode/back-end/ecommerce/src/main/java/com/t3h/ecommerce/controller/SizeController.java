package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.admin_size.SizeAdminAddDTO;
import com.t3h.ecommerce.dto.request.admin_size.SizeAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class SizeController {

    private final SizeService service;

    @GetMapping("/public/sizes")
    public BaseResponse<?> getAllSize(){
        return service.getAllSize();
    }


    @PostMapping("/admin/sizes")
    public BaseResponse<?> findSize(@Valid @RequestBody SizeAdminRequest request){
        return service.findSize(request);
    }

    @DeleteMapping("/admin/size")
    public BaseResponse<?> deleteSize(@Valid @RequestParam("ids") String ids){
        return service.deleteSize(ids);
    }

    @PostMapping("/admin/size")
    public BaseResponse<?> createOrEdit(@Valid @RequestBody SizeAdminAddDTO request){
        return service.createOrEdit(request);
    }

}
