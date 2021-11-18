package com.costcommission.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class User extends BaseEntity {

    private String fullName;
    private String loginId;
    private String password;
    private String emailId;
    private String location;
    private Integer active;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="designation_id")
    private Designation designation;

}
