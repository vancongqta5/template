package com.t3h.ecommerce.pojo.dto.cart;


import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrModelRequest {
    private PageRequest pageRequest;
}
