package com.t3h.ecommerce.dto.request;


import lombok.Data;

@Data
public class FilterDate {
    private Long createdDateStart;
    private Long createdDateEnd;
    private Long updatedDateStart;
    private Long updatedDateEnd;
}
