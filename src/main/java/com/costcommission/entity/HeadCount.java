package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class HeadCount extends BaseEntity{

    private String period;
    private Long allCCBEmployees;
    private Long inWhichContainerShip;

}
