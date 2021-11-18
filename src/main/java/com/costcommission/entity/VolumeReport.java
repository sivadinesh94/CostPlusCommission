package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Getter
@Setter
@Entity
public class VolumeReport extends BaseEntity {

    private Long year;
    private Integer month;
    private Double countOfBOLNumber;
    private Double sumOfTues;
    private String type;
    private Long costCenterId;

}
