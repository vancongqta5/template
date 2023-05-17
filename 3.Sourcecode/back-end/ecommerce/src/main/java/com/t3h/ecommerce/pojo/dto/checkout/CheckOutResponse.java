package com.t3h.ecommerce.pojo.dto.checkout;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckOutResponse {
    private List<CheckoutDTO> products = new ArrayList<>();
    private Double totalMany;
}
