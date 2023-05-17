package com.t3h.ecommerce.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_date", updatable = false)
    @CreatedDate
    protected Long createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    protected Long updatedDate;

    public BaseEntity(Long createdDate, Long updatedDate) {
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public BaseEntity(Long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public BaseEntity() {
    }
}
