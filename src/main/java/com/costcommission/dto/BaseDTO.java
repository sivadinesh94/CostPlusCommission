package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseDTO {

    private Long id;
    private boolean deleted;

}
