package com.t3h.ecommerce.pojo.dto.product_detail;

import com.t3h.ecommerce.entities.product.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeDTO {
    private Long id;
    private String sizeCode;

    public SizeDTO(Size size){
        BeanUtils.copyProperties(size, this);
    }
}
