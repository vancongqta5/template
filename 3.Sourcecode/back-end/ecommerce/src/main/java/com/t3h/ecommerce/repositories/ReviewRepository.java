package com.t3h.ecommerce.repositories;

import com.t3h.ecommerce.entities.product.Review;
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
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join r.product p where p.id =:ids")
    Page<Review> findReviewByProductId(Pageable pageable,
                                       @Param("ids") Long id);


//    @Query("select r from Review  r join  r.product p where  p.id =:")

}
