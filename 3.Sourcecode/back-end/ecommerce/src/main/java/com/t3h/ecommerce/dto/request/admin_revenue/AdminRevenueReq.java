package com.t3h.ecommerce.dto.request.admin_revenue;


import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRevenueReq {
    private FilterDate filterDate;
    private PageRequest pageRequest;
    private String productName;
    private List<Long> idsCategory;
}
