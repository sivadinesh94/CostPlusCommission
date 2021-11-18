package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleAgencyDTO extends BaseDTO{
    private Long userRoleId;
    private Long agencyId;
    private String agencyName;
}
