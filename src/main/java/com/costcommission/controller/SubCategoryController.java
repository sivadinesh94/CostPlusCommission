package com.costcommission.controller;

import com.costcommission.dto.ResponseDTO;
import com.costcommission.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subCategory")
public class SubCategoryController {

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/allActive/{categoryId}")
    public ResponseEntity<Object> allActive(@PathVariable Long categoryId) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Sub category fetched successfully", true, subCategoryService.allActiveByCategoryId(categoryId));
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }
}
