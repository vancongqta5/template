package com.t3h.ecommerce.entities.order;

import com.t3h.ecommerce.entities.BaseEntity;
import com.t3h.ecommerce.entities.product.Discount;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "payment_type")
public class PaymentType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "payment_name")
    private String paymentName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "paymentType", fetch = FetchType.LAZY)
    private List<Orders> orders;

}
