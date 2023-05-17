package com.t3h.ecommerce.entities.product;


import com.t3h.ecommerce.entities.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "color")
public class Color extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "color_name")
    private String colorName;

    @Column(name ="color_code")
    private String colorCode;

    @OneToMany(mappedBy = "color",fetch = FetchType.LAZY)
    private List<Product> product;

    public Color() {

    }

    public Color(Long createdDate, Long updatedDate, String colorName, String colorCode) {
        super(createdDate, updatedDate);
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

    public Color(Long createdDate, Long updatedDate, Long id, String colorName, String colorCode) {
        super(createdDate, updatedDate);
        this.id = id;
        this.colorName = colorName;
        this.colorCode = colorCode;
    }
}
