package com.t3h.ecommerce.controller;
import com.t3h.ecommerce.dto.request.admin_color.ColorAdminAdd;
import com.t3h.ecommerce.dto.request.admin_color.ColorAdminRequest;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class ColorController {

    private final ColorService service;

    @GetMapping("/public/colors")
    public BaseResponse<?> getAllColor(){
        return service.getAllColor();
    }


    @PostMapping("/admin/colors")
    public BaseResponse<?> findColor(@Valid @RequestBody ColorAdminRequest request){
        return service.findColor(request);
    }

    @DeleteMapping("admin/color")
    public BaseResponse<?> deleteColor(@Valid @RequestParam("ids") String ids){
        return service.deleteColor(ids);
    }

    @PostMapping("/admin/color")
    public BaseResponse<?> createOrEdit(@Valid @RequestBody ColorAdminAdd request){
        return service.createOrEdit(request);
    }


}
