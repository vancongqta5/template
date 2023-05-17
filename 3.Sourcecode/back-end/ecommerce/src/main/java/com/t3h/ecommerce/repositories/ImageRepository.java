package com.t3h.ecommerce.repositories;


import com.t3h.ecommerce.entities.product.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "select * from Image I where I.product_id =:id", nativeQuery = true)
    List<Image> findByProductId(@Param("id") Long id);

    @Modifying
    @Query(value = "delete from Image i where i.product_id =:id", nativeQuery = true)
    void deleteByProductId(@Param("id") Long id);

    @Modifying
    @Query(value = "delete from image i where i.product_id in :ids", nativeQuery = true)
    void deleteImageByListProduct(@Param("ids") List<Long> ids);


}
