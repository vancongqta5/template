package com.t3h.ecommerce.entities.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "category")
@Data
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;


    @Column(name = "description", columnDefinition = "text")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    public Category(Long createdDate, Long updatedDate, String categoryName, String description) {
        super(createdDate, updatedDate);
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(Long id, Long createdDate, Long updatedDate, String categoryName, String description) {
        super(createdDate, updatedDate);
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category() {
        super();
    }
}
