package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class UserRole extends BaseEntity {

    private Long userId;
    private Long roleId;

}
