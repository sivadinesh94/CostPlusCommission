package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDTO extends BaseDTO {

    private Long userId;
    private Long roleId;
    private String roleName;

}
