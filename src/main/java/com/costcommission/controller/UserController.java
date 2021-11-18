package com.costcommission.controller;

import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.dto.UserDTO;
import com.costcommission.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DesignationService designationService;

    @Autowired
    private CostPlusTemplateService costPlusTemplateService;

    @PostMapping("/excel")
    public ResponseEntity<Object> createExcel() {
        ResponseDTO responseDTO;
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            costPlusTemplateService.generate();
            responseDTO = new ResponseDTO("successfully created", true, null);
        } catch (Exception e) {
            responseDTO = new ResponseDTO("failure to create", false, null);
            httpStatus = HttpStatus.EXPECTATION_FAILED;
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }


    @GetMapping
    public ResponseEntity<Object> findAll() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("User fetched successfully", true, userService.findAll());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserDTO userDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            userService.create(userDTO);
            responseDTO = new ResponseDTO("User created successfully", true, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody UserDTO userDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            userService.update(userDTO);
            responseDTO = new ResponseDTO("User updated successfully", true, null);
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @GetMapping("/filterByPagination")
    public ResponseEntity<Object> getByPaginationAndSpecification
            (@RequestParam(required = false, defaultValue = "updatedTimeStamp") String column,
             @RequestParam(required = false, defaultValue = "1") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer size,
             @RequestParam(required = false, defaultValue = "false") Boolean asc,
             @RequestBody SpecificationDTO specificationDTO){
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Agency fetched successfully", true, userService.getByPaginationAndSpecification(column, page, size, asc, specificationDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
