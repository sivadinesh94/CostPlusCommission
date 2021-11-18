package com.costcommission.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Agency extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String code;
    @Column(unique = true, nullable = false)
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
    private String arTempRecipient;
    private String managerTempRecipient;
    private String agencyTempRecipient;
    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name="cluster_id")
    private Cluster cluster;





}
