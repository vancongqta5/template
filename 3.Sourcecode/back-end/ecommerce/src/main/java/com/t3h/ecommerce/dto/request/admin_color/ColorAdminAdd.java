package com.t3h.ecommerce.dto.request.admin_color;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorAdminAdd {
    private Long id;
    private String colorName;
    private String colorCode;
}
