package com.t3h.ecommerce.dto.request.admin_size;

import com.t3h.ecommerce.entities.product.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeAdminDTO {
    private Long id;
    private String sizeCode;
    private String sizeName;
    private Long createdDate;
    private Long updatedDate;

    public SizeAdminDTO(Size size){
        BeanUtils.copyProperties(size, this);
    }
}
