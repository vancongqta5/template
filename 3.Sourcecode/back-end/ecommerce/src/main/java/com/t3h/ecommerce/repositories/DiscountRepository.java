package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.entities.product.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    @Query("select d from Discount d where d.discountName like concat('%',:discountName, '%') and " +
            "(d.discountPercent =:discountPercent or :discountPercent = -1) and " +
            "((d.createdDate between :createdDateStart and :createdDateEnd) or :createdDateStart=0) and " +
            "((d.updatedDate between :updatedDateStart and :updatedDateEnd) or :updatedDateStart=0)")
      Page<Discount> findDiscount(Pageable pageable,
                                  @Param("discountName") String discountName,
                                  @Param("discountPercent") Float discountPercent,
                                  @Param("createdDateStart") Long createdDateStart,
                                  @Param("createdDateEnd") Long createdDateEnd,
                                  @Param("updatedDateStart") Long updatedDateStart,
                                  @Param("updatedDateEnd") Long updatedDateEnd);


    @Modifying
    @Query("delete from Discount d where d.id in :ids")
    void deleteDiscount(@Param("ids") List<Long> ids);
}
