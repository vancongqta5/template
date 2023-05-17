package com.t3h.ecommerce.pojo.dto.product_detail;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailDTO {
    private Long id;
    private String productName;
    private Float cost;
    private String shortDescription;
    private String description;
    private String categoryName;
    private List<ColorDTO> colorDTOList;
    private List<SizeDTO> sizeDTOList;
    private List<ImageDTO> imageDTOList;
}
