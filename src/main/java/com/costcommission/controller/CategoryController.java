package com.costcommission.controller;

import com.costcommission.dto.ResponseDTO;
import com.costcommission.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/allActive")
    public ResponseEntity<Object> allActive() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Category fetched successfully", true, categoryService.allActive());
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
