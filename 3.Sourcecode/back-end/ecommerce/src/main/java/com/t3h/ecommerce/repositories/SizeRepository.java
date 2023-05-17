package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.product.Color;
import com.t3h.ecommerce.entities.product.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Size, Long> {

    @Query("select s from Size s where s.sizeName like concat('%',:sizeName, '%') and " +
            "s.sizeCode like concat('%', :sizeCode, '%') and " +
            "((s.createdDate between :createdDateStart and :createdDateEnd) or :createdDateStart=0) and " +
            "((s.updatedDate between :updatedDateStart and :updatedDateEnd) or :updatedDateStart=0)")
    Page<Size> findSize(Pageable pageable,
                          @Param("sizeName") String colorName,
                          @Param("sizeCode") String colorCode,
                          @Param("createdDateStart") Long createdDateStart,
                          @Param("createdDateEnd") Long createdDateEnd,
                          @Param("updatedDateStart") Long updatedDateStart,
                          @Param("updatedDateEnd") Long updatedDateEnd);


    @Modifying
    @Query("delete from Size s where s.id in :ids")
    void deleteSize(@Param("ids") List<Long> ids);


    @Query("select s from Size s join s.product p where p.id = :ids")
    List<Size> findSizeByProductId(@Param("ids") Long id);
}
