package com.t3h.ecommerce.pojo.dto.checkout;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
   private List<CheckoutModel> request;
}
