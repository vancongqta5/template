package com.t3h.ecommerce.dto.request.admin_size;

import com.t3h.ecommerce.dto.request.FilterDate;
import com.t3h.ecommerce.dto.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeAdminRequest {
    private FilterDate filterDate;
    private PageRequest pageRequest;
    private String sizeName;
    private String sizeCode;
}
