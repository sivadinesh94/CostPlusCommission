package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role extends BaseEntity {

    private String name;

}
