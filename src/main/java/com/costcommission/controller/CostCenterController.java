package com.costcommission.controller;

import com.costcommission.dto.CostCenterDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.service.CostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/costCenter")
public class CostCenterController {
    @Autowired
    private CostCenterService costCenterService;

    @GetMapping("/{type}")
    public ResponseEntity<Object> findAll(@PathVariable("type") String type) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cost Center fetched successfully", true, costCenterService.findAllCostCenter(type));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CostCenterDTO costCenterDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cost Center created successfully", true, costCenterService.create(costCenterDTO));
            httpStatus = HttpStatus.OK;
        } catch (MasterDataAlreadyExistException me) {
            me.printStackTrace();
            responseDTO = new ResponseDTO(me.getMessage(), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO("Cost Center creation failed", false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody CostCenterDTO costCenterDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cost Center Updated successfully", true, costCenterService.update(costCenterDTO));
            httpStatus = HttpStatus.OK;
        } catch (MasterDataAlreadyExistException me) {
            me.printStackTrace();
            responseDTO = new ResponseDTO(me.getMessage(), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO("Cost Center update failed", false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping("/{id}/enableOrDisable")
    public ResponseEntity<Object> inActivate(@PathVariable Long id, @RequestParam Boolean status) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cost Center Inactivated successfully", true, costCenterService.enableOrDisable(id, status));
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
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
            responseDTO = new ResponseDTO("Cost center fetched successfully", true, costCenterService.getByPaginationAndSpecification(column, page, size, asc, specificationDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
