package com.t3h.ecommerce.pojo.dto.product_detail;


import com.t3h.ecommerce.entities.product.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String url;

    public ImageDTO(Image image){
        BeanUtils.copyProperties(image, this);
    }
}
