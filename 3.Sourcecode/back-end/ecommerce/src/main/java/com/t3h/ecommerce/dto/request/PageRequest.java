package com.t3h.ecommerce.dto.request;

import com.t3h.ecommerce.pojo.dto.product.CostRequestPage;
import com.t3h.ecommerce.pojo.dto.product.QuantityRequestPage;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
         private int page;
         private int pageSize;
         private String sortBy;
         private String condition;
}
