package com.costcommission.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RoleAgencyDTO {

    private Long roleId;
    private String roleName;
    private Long agencyId;
    private String agencyName;
    private Long userRoleId;
    private Long userRoleAgencyId;

}
