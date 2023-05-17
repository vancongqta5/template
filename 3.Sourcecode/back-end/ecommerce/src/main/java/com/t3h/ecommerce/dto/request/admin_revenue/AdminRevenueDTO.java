package com.t3h.ecommerce.dto.request.admin_revenue;


import com.t3h.ecommerce.entities.order.Revenue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRevenueDTO{
    private Long totalMoney;
    private String categoryName;
    private String productName;
    private Long totalQuantity;
    private Long quantitySold;
    private Long inventory;
    private Long createdDate;
    private Long updatedDate;

    public AdminRevenueDTO(Revenue revenue){
        BeanUtils.copyProperties(revenue, this);
    }
}
