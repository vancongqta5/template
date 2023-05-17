package com.t3h.ecommerce.pojo.dto.product_detail;


import com.t3h.ecommerce.entities.product.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorDTO {
    private Long id;
    private String colorName;

    public ColorDTO(Color color){
        BeanUtils.copyProperties(color, this);
    }
}
