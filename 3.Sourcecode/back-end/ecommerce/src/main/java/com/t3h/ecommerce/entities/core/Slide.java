package com.t3h.ecommerce.entities.core;


import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "slide")
public class Slide extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "slide_url")
    private String slideUrl;
}
