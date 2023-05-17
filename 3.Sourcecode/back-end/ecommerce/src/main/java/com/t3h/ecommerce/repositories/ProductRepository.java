package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.dto.request.admin_product.ProductAdmin;
import com.t3h.ecommerce.dto.request.admin_product.ProductAdminRequest;
import com.t3h.ecommerce.dto.request.shop_product.ShopProduct;
import com.t3h.ecommerce.dto.request.shop_product.ShopProductDTO;
import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {

   @Query("SELECT\n" +
           "    P.id as id, P.productName as productName, P.shortDescription as shortDescription," +
           "P.cost as cost, P.quantity as quantity, P.createdDate as createdDate, P.updatedDate as updatedDate," +
           " c.id as categoryId, d.id as discountId, co.id as colorId, s.id as sizeId\n" +
           "FROM Product AS P JOIN P.category c join P.discount d join P.color co join P.size s\n" +
           "WHERE (c.id =:categoryId or :categoryId=0) " +
           "and (P.productName LIKE concat('%', :productName, '%')) and\n" +
           "((P.quantity between :quantity and :quantity+10) or :quantity=0) and\n" +
           "((P.cost between :cost and :cost+100) or :cost=0) and\n" +
           "((P.createdDate between :createdDateStart and :createdDateEnd) or :createdDateStart=0) and\n" +
           "((P.updatedDate between :updatedDateStart and :updatedDateEnd) or :updatedDateStart=0) " )
   Page<ProductAdmin> findProduct(Pageable pageable,
                                  @Param("productName") String productName,
                                  @Param("quantity") Long quantity,
                                  @Param("cost") Double cost,
                                  @Param("categoryId") Long categoryId,
                                  @Param("createdDateStart") Long createdDateStart,
                                  @Param("createdDateEnd") Long createdDateEnd,
                                  @Param("updatedDateStart") Long updatedDateStart,
                                  @Param("updatedDateEnd") Long updatedDateEnd);


   @Modifying
   @Query("delete from Product p where p.id in :ids")
   void deleteProduct(@Param("ids") List<Long> ids);

   @Modifying
   @Query(value = "delete from product p where p.category_id in :ids", nativeQuery = true)
   void deleteProductByCategory(@Param("ids") List<Long> ids);

   @Modifying
   @Query(value = "delete from product p where p.color_id in :ids", nativeQuery = true)
   void deleteProductByColor(@Param("ids") List<Long> ids);

   @Modifying
   @Query(value = "delete from product p where p.size_id in :ids" , nativeQuery = true)
   void deleteProductBySize(@Param("ids") List<Long> ids);

   @Modifying
   @Query( value = "delete from product p where p.discount_id in :ids",nativeQuery = true)
   void deleteProductByDiscount(@Param("ids") List<Long> ids);

   @Query(value = "select p from Product  p")
   Page<Product> findProductForHome(Pageable pageable);


   @Query("select p from Product p where p.id in :ids")
   List<Product> getAllProduct(@Param("ids") List<Long> ids);


   @Query(value ="SELECT " +
           "P.id as id , p.product_name as productName, P.cost as cost , p.img_avt as imgAvt , d.discount_name as discountName ,P.category_id as categoryID, c.category_name as categoryName\n" +
           "from Product P\n" +
           "    join discount d on d.id = P.discount_id\n" +
           "    join category c on c.id = P.category_id\n" +
           "where p.product_name like concat('%',:productName,'%') \n" +
           "  and (:cost = 0 or (p.cost between :cost and :cost + 100 )) \n" +
           "  and (:categoryId = 0 or p.category_id = :categoryId )" ,
           countQuery = "SELECT P.id as id , p.product_name as productName, P.cost as cost , p.img_avt as imgAvt , d.discount_name as discountName ,P.category_id as categoryID, c.category_name as categoryName\n" +
           "from Product P\n" +
           "    join discount d on d.id = P.discount_id\n" +
           "    join category c on c.id = P.category_id\n" +
           "where p.product_name like concat('%',:productName,'%') \n" +
           "  and  (:cost = 0 or (p.cost between :cost and :cost + 100 ))  \n" +
           "  and (:categoryId = 0 or p.category_id = :categoryId )" ,nativeQuery = true)
   Page<ShopProduct> findProductForShopPage(Pageable pageable,
                                                    @Param("productName") String productName,
                                                    @Param("cost") Double cost,
                                                    @Param("categoryId") Long categoryID);



}
