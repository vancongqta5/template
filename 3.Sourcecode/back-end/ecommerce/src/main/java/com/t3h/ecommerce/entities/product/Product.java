package com.t3h.ecommerce.entities.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.t3h.ecommerce.entities.BaseEntity;
import com.t3h.ecommerce.entities.order.OrderDetail;
import com.t3h.ecommerce.entities.order.Orders;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@Table(name = "product")
@SuperBuilder
@Builder
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name ="img_avt")
    private String imgAvt;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "quantity")
    private Long quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne
    @JoinColumn(name = "discount_id", nullable = false)
    private Discount discount;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    public Product(Long createdDate, Long updatedDate, String productName, String shortDescription,
                   String description, String imgAvt, Double cost,
                   Long quantity, Category category,
                   Size size, Discount discount, Color color) {
        super(createdDate, updatedDate);
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.description = description;
        this.imgAvt = imgAvt;
        this.cost = cost;
        this.quantity = quantity;
        this.category = category;
        this.size = size;
        this.discount = discount;
        this.color = color;
    }

    public Product() {

    }
}
