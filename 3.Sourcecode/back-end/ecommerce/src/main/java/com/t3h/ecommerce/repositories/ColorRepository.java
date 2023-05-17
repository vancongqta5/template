package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.product.Color;
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
public interface ColorRepository extends JpaRepository<Color, Long> {


    @Query("select c from Color c where c.colorName like concat('%',:colorName, '%') and " +
            "c.colorCode like concat('%', :colorCode, '%') and " +
            "((c.createdDate between :createdDateStart and :createdDateEnd) or :createdDateStart=0) and " +
            "((c.updatedDate between :updatedDateStart and :updatedDateEnd) or :updatedDateStart=0)")
    Page<Color> findColor(Pageable pageable,
                          @Param("colorName") String colorName,
                          @Param("colorCode") String colorCode,
                          @Param("createdDateStart") Long createdDateStart,
                          @Param("createdDateEnd") Long createdDateEnd,
                          @Param("updatedDateStart") Long updatedDateStart,
                          @Param("updatedDateEnd") Long updatedDateEnd);


    @Modifying
    @Query("delete from Color c where c.id in :ids")
    void deleteColor(@Param("ids") List<Long> ids);

    @Query("select c from Color c join c.product p where p.id = :ids")
    List<Color> findColorByProductId(@Param("ids") Long id);
}
