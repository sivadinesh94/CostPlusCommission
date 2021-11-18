package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoaDTO extends  BaseDTO {
    private String natureCode;
    private String natureDesc;
    private String description;
    private String safranpl;
    private String safranplDetails;
    private String agencyBudgetCode;
    private String agencyBudgetLine;
    private String mandatoryAllocPerCC;
    private String unallocatedExpenses;
    private String costCenterAllocation;
    private String natureNotRelevant;
    private String rationale;
    private String costPlusMatrix;
    private String descriptionEnglish;
    private Boolean isActive;

}
