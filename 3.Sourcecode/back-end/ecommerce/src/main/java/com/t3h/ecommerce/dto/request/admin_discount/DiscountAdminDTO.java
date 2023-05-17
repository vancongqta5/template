package com.t3h.ecommerce.dto.request.admin_discount;


import com.t3h.ecommerce.entities.product.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAdminDTO {
    private Long id;
    private String discountName;
    private Float discountPercent;
    private Long createdDate;
    private Long updatedDate;

    public DiscountAdminDTO(Discount discount){
        BeanUtils.copyProperties(discount, this);
    }
}
