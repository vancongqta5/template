package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.product.Product;
import com.t3h.ecommerce.pojo.dto.product_detail.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<Product, Long> {


    @Query("select p.id as id, p.productName as productName," +
            "p.cost as cost, p.shortDescription as shortDescription," +
            "p.description as description, c.categoryName as categoryName" +
            " from Product p join p.category c where p.id =:id")
    ProductDetail findProduct(@Param("id") Long id);
}
