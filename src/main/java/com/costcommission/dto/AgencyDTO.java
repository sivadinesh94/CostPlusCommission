package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
public class AgencyDTO extends BaseDTO {

    private String code;
    private String name;
    private String description;
    private Long safEntityNumber;
    private String legalEntityName;
    private String oceanType;
    private Integer markup;
    private Boolean headCountFlag;
    private String fcCcy;
    private String location;
    private String templateName;
    private String ledgerName;
    private String company;
    private String site;
    private String debitAccount;
    private String creditAccount;
    private String natureCode;
    private Boolean isVatGst;
    private Integer number;
    private Long clusterId;
    private String clusterName;
    private String arTempRecipient;
    private String managerTempRecipient;
    private String agencyTempRecipient;
    private Boolean isActive = true;


}
