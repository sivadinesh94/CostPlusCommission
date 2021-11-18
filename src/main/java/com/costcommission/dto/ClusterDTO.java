package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClusterDTO extends BaseDTO {

    private String code;
    private String name;
    private Boolean isActive = true;
    private Long regionId;
    private String regionName;

}
