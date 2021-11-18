package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Region extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String code;
    @Column(unique = true,nullable = false)
    private String name;
    private Boolean isActive = true;

    @OneToMany(mappedBy = "region", cascade= CascadeType.ALL)
    List<Cluster> clusters;

}

