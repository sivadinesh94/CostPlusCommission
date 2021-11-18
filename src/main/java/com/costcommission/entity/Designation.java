package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class Designation extends BaseEntity {

    private String name;
    @OneToOne(mappedBy = "designation")
    private User user;

}
