package com.t3h.ecommerce.dto.request.admin_color;

import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorAdminRequest {
    FilterDate filterDate;
    PageRequest pageRequest;
    String colorName;
    String colorCode;
}
