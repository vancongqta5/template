package com.t3h.ecommerce.entities.product;


import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "review")
@Entity
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public Review(Long createdDate, Long updatedDate,
                  Float rating, String comment,
                  Long userId) {
        super(createdDate, updatedDate);
        this.rating = rating;
        this.comment = comment;
        this.userId = userId;
    }

    public Review() {

    }
}
