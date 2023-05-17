package com.t3h.ecommerce.pojo.dto.revenue;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueRequestDTO {
    List<RevenueModel> request = new ArrayList<>();
}
