package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class PeriodControl extends BaseEntity{

    private Long agencyId;
    private String month;
    private String year;
    private Boolean status;

}
