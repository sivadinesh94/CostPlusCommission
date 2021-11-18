package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class CostCenter extends BaseEntity{

    private String code;
    private String name;
    private Integer sequence;
    private String keyAllocation;
    private Boolean status;
    private String type;
    private String carrierCode;
    private String carrierName;

    @ManyToOne
    private SubCategory subCategory;
}
