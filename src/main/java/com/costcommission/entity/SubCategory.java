package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class SubCategory extends BaseEntity {

    private String name;
    private Integer subCategorySeq;
    @ManyToOne
    private Category category;
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="subcategory_id")
    private List<CostCenter> costCenters;
}
