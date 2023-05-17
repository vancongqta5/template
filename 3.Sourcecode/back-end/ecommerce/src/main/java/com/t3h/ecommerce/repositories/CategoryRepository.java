package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.entities.product.Category;
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
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("select c from Category c where c.categoryName like concat('%', :categoryName, '%') and " +
            "c.description like concat('%',:description, '%') and " +
            "((c.createdDate between :createdDateStart and :createdDateEnd) or (:createdDateStart =0)) and " +
            "((c.updatedDate between :updatedDateStart and :updateDateEnd) or (:updatedDateStart =0))")
    Page<Category>  findCategory(Pageable pageable,
                                 @Param("categoryName") String categoryName,
                                 @Param("description") String description,
                                 @Param("createdDateStart") Long createdDateStart,
                                 @Param("createdDateEnd") Long createdDateEnd,
                                 @Param("updatedDateStart") Long updatedDateStart,
                                 @Param("updateDateEnd") Long updateDateEnd);



    @Modifying
    @Query("delete from Category c where c.id in :ids")
    void deleteCategory(@Param("ids") List<Long> ids);
}
