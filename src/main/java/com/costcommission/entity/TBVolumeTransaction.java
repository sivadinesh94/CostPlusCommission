package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class TBVolumeTransaction extends BaseEntity{

    private String fileName;
    private Long agencyId;
    private String type;
    private String fileType;

}
