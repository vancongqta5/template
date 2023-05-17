package com.t3h.ecommerce.dto.request.admin_category;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddDTO {
    private Long id;
    private String categoryName;
    private String description;
}
