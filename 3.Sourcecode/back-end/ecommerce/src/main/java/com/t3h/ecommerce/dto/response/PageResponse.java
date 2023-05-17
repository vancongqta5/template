package com.t3h.ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> data = Collections.emptyList();
    private Integer totalPage;
    private Long totalItems;
    private String message;
    private Integer httpStatusCode;
}
