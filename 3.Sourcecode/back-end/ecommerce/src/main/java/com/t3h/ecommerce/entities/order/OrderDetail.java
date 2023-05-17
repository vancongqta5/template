package com.t3h.ecommerce.entities.order;

import com.t3h.ecommerce.entities.BaseEntity;
import com.t3h.ecommerce.entities.product.Category;
import com.t3h.ecommerce.entities.product.Product;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "discount_percent")
    private Float discountPercent;

    @Column(name = "order_detail_status")
    private Integer orderDetailStatus;

    @Column(name = "date_allocated")
    private Long dateAllocated;

    @Column(name = "total_money")
    private long totalMoney;

    @ManyToOne
    @JoinColumn(name = "revenue_id")
    private Revenue revenue;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product = new Product();
}
