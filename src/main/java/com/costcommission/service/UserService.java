package com.costcommission.service;

import com.costcommission.dto.*;
import com.costcommission.entity.*;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.jpaspecification.AgencySpecification;
import com.costcommission.jpaspecification.UserSpecification;
import com.costcommission.repository.*;
import com.costcommission.util.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRoleAgencyRepository userRoleAgencyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DesignationRepository designationRepository;

    public UserDTO findAll() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Type listType = new TypeToken<List<AgencyDTO>>() {
        }.getType();
        return modelMapper.map(userRepository.findAll(), listType);
    }

    public void create(UserDTO userDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = modelMapper.map(userDTO, User.class);
        Designation designation = designationRepository.findDesignationById(userDTO.getDesignationId());
        user.setDesignation(designation);
        userRepository.save(user);
        for(RoleAgencyDTO roleAgencyDTO : userDTO.getRoleAgencyDTOS()) {
            UserRoleAgency userRoleAgency = new UserRoleAgency();
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleAgencyDTO.getRoleId());
            userRole.setUserId(user.getId());
            userRoleRepository.save(userRole);
            userRoleAgency.setUserRoleId(userRole.getId());
            userRoleAgency.setAgencyId(roleAgencyDTO.getAgencyId());
            userRoleAgencyRepository.save(userRoleAgency);
        }
    }

    public void update(UserDTO userDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        User user = modelMapper.map(userDTO, User.class);
        Designation designation = designationRepository.findDesignationById(userDTO.getDesignationId());
        user.setDesignation(designation);
        userRepository.save(user);
        for(RoleAgencyDTO roleAgencyDTO : userDTO.getRoleAgencyDTOS()) {
            UserRoleAgency userRoleAgency = new UserRoleAgency();
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleAgencyDTO.getRoleId());
            userRole.setUserId(user.getId());
            userRoleRepository.save(userRole);
            userRoleAgency.setUserRoleId(userRole.getId());
            userRoleAgency.setAgencyId(roleAgencyDTO.getAgencyId());
            userRoleAgencyRepository.save(userRoleAgency);
        }
    }

    public void deleteUserRole() {

    }

    public void deleteUserRoleAgency() {

    }

    public PaginationResponseDTO getByPaginationAndSpecification(String column, int page, int size, Boolean asc, SpecificationDTO specificationDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
        Sort.Direction sort = Sort.Direction.DESC;
        if(asc) {
            sort = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, sort, column);
        List<UserDTO> userDTOS = new ArrayList<>();
        UserSpecification spec = new UserSpecification(specificationDTO);
        Page<User> users = userRepository.findAll(spec, pageable);
        for(User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTO.setDesignationId(user.getDesignation().getId());
            userDTO.setDesignationName(user.getDesignation().getName());
            List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
            List<RoleAgencyDTO> roleAgencyDTOS = new ArrayList<>();
            for(UserRole userRole : userRoles) {
                Role role = roleRepository.findRoleById(userRole.getRoleId());
                RoleAgencyDTO roleAgencyDTO = new RoleAgencyDTO();
                roleAgencyDTO.setRoleId(role.getId());
            }

            //userDTO.setRoleAgencyDTOS();
            userDTOS.add(userDTO);
        }
        paginationResponseDTO.setTotalSize(users.getTotalElements());
        paginationResponseDTO.setData(userDTOS);
        return paginationResponseDTO;
    }

}



