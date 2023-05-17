package com.t3h.ecommerce.dto.request.admin_category;

import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryAdminRequest {
    PageRequest pageRequest;
    FilterDate filterDate;
    String categoryName;
    String description;
}
