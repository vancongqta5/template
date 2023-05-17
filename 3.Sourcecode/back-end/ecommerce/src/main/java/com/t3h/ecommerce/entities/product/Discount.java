package com.t3h.ecommerce.entities.product;

import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "discount")
public class Discount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "discount_Name")
    private String discountName;


    @Column(name = "discount_percent")
    private Float discountPercent;

    @OneToMany(mappedBy = "discount", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product;


    public Discount(Long createdDate, Long updatedDate,
                    Long id, String discountName, Float discountPercent) {
        super(createdDate, updatedDate);
        this.id = id;
        this.discountName = discountName;
        this.discountPercent = discountPercent;
    }

    public Discount(Long createdDate, Long updatedDate,
                    String discountName, Float discountPercent) {
        super(createdDate, updatedDate);
        this.discountName = discountName;
        this.discountPercent = discountPercent;
    }

    public Discount() {

    }
}
