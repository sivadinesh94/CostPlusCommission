package com.costcommission.controller;

import com.costcommission.dto.ResponseDTO;
import com.costcommission.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;

@RestController
@RequestMapping("/api/v1/designation")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping("/all")
    public ResponseEntity<Object> findAllDesignation() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Designations fetched successfully", true, designationService.findAllDesignation());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
