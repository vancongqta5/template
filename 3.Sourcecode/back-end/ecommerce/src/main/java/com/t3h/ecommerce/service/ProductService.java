package com.t3h.ecommerce.service;


import com.t3h.ecommerce.dto.request.PageRequest;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminAddRequest;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminDTO;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminRequest;
import com.t3h.ecommerce.dto.request.shop_product.ShopProductDTO;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.pojo.dto.product.ProductDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

    BaseResponse<?> findProduct(ProductAdminRequest request);

    BaseResponse<?> deleteProduct(String Ids);

    BaseResponse<?> findProductForShop(ProductAdminRequest request);
 
    BaseResponse<?> createOrEditProduct(ProductAdminAddRequest request);

    BaseResponse<?> findProductById(String id);

    BaseResponse<?> findProductForHome(PageRequest pageRequest);

    ResponseEntity<Resource> exportExcelProduct();

    BaseResponse<?> importExcelProduct(MultipartFile importFile);

//    ResponseEntity<?> getProductForShop(Pageable pageable,String productName);
 
}
