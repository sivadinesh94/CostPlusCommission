package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class CostPlusRemuneration extends BaseEntity {

    private Long agencyId;
    private Long costCenterId;
    private Double amount;
    private Integer year;
    private Integer month;

}
