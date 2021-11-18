package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class COA extends BaseEntity{

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
