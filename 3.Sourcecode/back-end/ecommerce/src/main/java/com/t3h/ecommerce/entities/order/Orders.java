package com.t3h.ecommerce.entities.order;

import com.t3h.ecommerce.entities.BaseEntity;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.entities.product.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Orders")
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "order_date")
    private Long orderDate;

    @Column(name = "shipped_date")
    private Long shippedDate;

    @Column(name = "ship_name", length = 50)
    private String shipName;

    @Column(name = "ship_address", length = 500)
    private String shipAddress;

    @Column(name = "ship_city")
    private String shipCity;

    @Column(name = "ship_country")
    private String shipCountry;

    @Column(name = "ship_fee")
    private Double shipFee;

    @Column(name = "order_status")
    private Integer orderStatus;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", nullable = false)
    private PaymentType paymentType;


    @OneToMany(mappedBy = "orders", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
