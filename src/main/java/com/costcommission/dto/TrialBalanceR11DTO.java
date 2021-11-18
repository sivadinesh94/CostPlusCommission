package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class TrialBalanceR11DTO {

    private String company;
    private String site;
    private String account;
    private String accountDescription;
    private String nature;
    private String natureDescription;
    private String costCenter;
    private String costCenterDescription;
    private String partner;
    private String partnerDescription;
    private String currencyCode;
    private String beginBalance;
    private String periodNetDebit;
    private String periodNetCredit;
    private Double closingBalance;

}
