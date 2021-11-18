package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseEntity {

    private String name;
    private Integer categorySeq;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="category_id")
    private List<SubCategory> subCategories;

}
