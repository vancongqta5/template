package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductReviewAdd;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductReviewReq;

public interface ProductDetailService {


    BaseResponse<?> findProductDetail(String id);

    BaseResponse<?> findReview(ProductReviewReq req);

    BaseResponse<?> createReview(ProductReviewAdd req);
}
