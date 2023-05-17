package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.order.Revenue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("select r from Revenue r where (r.categoryId in :ids or :checkIds = -1) " +
            "and r.productName like concat('%', :productName, '%') and " +
            "((r.createdDate between :createdStart and :createdDateEnd) or :createdStart =0)")
    Page<Revenue> findRevenue(Pageable pageable,
                              @Param("ids") List<Long> ids,
                              @Param("checkIds") Integer checkIds,
                              @Param("productName") String productName,
                              @Param("createdStart") Long createdStart,
                              @Param("createdDateEnd") Long createdDateEnd);

    Revenue findRevenueByProductId(Long productId);
}
