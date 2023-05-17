package com.t3h.ecommerce.entities.order;

import com.t3h.ecommerce.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "revenue")
public class Revenue extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_money")
    private Long totalMoney;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "product_name")
    private String productName;

    @Column(name="total_quatity")
    private Long totalQuantity;

    @Column(name="quantity_sold")
    private Long quantitySold;

    @Column(name = "inventory")
    private Long inventory;

    @OneToMany(mappedBy = "revenue", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    public Revenue(Long createdDate, Long updatedDate, Long totalMoney,
                   Long productId, Long categoryId, String categoryName,
                   String productName, Long totalQuantity, Long quantitySold,
                   Long inventory) {
        super(createdDate, updatedDate);
        this.totalMoney = totalMoney;
        this.productId = productId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.quantitySold = quantitySold;
        this.inventory = inventory;
    }

    public Revenue(Long id, Long createdDate, Long updatedDate, Long totalMoney,
                   Long productId, Long categoryId, String categoryName,
                   String productName, Long totalQuantity, Long quantitySold,
                   Long inventory) {
        super(createdDate, updatedDate);
        this.id = id;
        this.totalMoney = totalMoney;
        this.productId = productId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.quantitySold = quantitySold;
        this.inventory = inventory;
    }
}
