package com.t3h.ecommerce.dto.request.admin_discount;

import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountAdminRequest {
    private FilterDate filterDate;
    private PageRequest pageRequest;
    private String discountName;
    private Float discountPercent;
}
