package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.entities.order.OrderDetail;
import com.t3h.ecommerce.pojo.dto.cart.CartDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long> {

    @Query("select p.productName as productName," +
            " p.imgAvt as imgAvt, p.id as productId, " +
            "p.cost as cost, r.inventory as inventory, o.quantity as quantity from OrderDetail o join o.product" +
            " p on p.id = o.product.id join o.revenue as r on r.id = o.revenue.id")
    Page<CartDB>  findAllCart(Pageable pageable);

    @Modifying
    @Query(value = "delete from order_detail as o where o.product_id =:productId ", nativeQuery = true)
    void deleteByProductId(@Param("productId") Long productId);


    OrderDetail findByProductId(Long id);
}
