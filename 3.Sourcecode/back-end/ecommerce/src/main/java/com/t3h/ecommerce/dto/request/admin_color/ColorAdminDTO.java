package com.t3h.ecommerce.dto.request.admin_color;


import com.t3h.ecommerce.entities.product.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorAdminDTO {
    private Long id;
    private String colorName;
    private String colorCode;
    private Long createdDate;
    private Long updatedDate;

    public ColorAdminDTO(Color color){
        BeanUtils.copyProperties(color, this);
    }
}
