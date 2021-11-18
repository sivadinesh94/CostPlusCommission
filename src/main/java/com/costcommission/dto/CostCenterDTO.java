package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCenterDTO extends BaseDTO{

    private String code;
    private String name;
    private Integer sequence;
    private String keyAllocation;
    private Boolean status;
    private String type;
    private String carrierCode;
    private String carrierName;
    private Long categoryId;
    private String categoryName;
    private Long subcategoryId;
    private String subcategoryName;

}
