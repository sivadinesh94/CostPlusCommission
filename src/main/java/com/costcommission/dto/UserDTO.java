package com.costcommission.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO extends BaseDTO {

    private String fullName;
    private String loginId;
    private String password;
    private String emailId;
    private String location;
    private Integer active;
    private Long designationId;
    private String designationName;
    private List<RoleAgencyDTO> roleAgencyDTOS;
    private List<RoleAgencyDisplayDTO> roleAgencyDisplayDTOS;

}
