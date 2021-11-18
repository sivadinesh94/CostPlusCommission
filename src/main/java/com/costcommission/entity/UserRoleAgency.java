package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class UserRoleAgency extends BaseEntity {

    private Long userRoleId;
    private Long agencyId;

}
