package com.t3h.ecommerce.entities.product;


import com.t3h.ecommerce.entities.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "size")
public class Size extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "size_code")
    private String sizeCode;

    @Column(name = "size_name")
    private String sizeName;


    @OneToMany(mappedBy = "size",fetch = FetchType.LAZY)
    private List<Product> product;

    public Size(Long createdDate, Long updatedDate,
                Long id, String sizeCode, String sizeName) {
        super(createdDate, updatedDate);
        this.id = id;
        this.sizeCode = sizeCode;
        this.sizeName = sizeName;
    }
    public Size(Long createdDate, Long updatedDate
                , String sizeCode, String sizeName) {
        super(createdDate, updatedDate);
        this.sizeCode = sizeCode;
        this.sizeName = sizeName;
    }


}
