package com.costcommission.controller;

import com.costcommission.dto.RegionDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody RegionDTO regionDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Region created successfully", true, regionService.create(regionDTO));
            httpStatus = HttpStatus.OK;
        } catch (MasterDataAlreadyExistException e) {
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody RegionDTO regionDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Region Updated successfully", true, regionService.update(regionDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping("/{id}/enableOrDisable")
    public ResponseEntity<Object> enableOrDisable(@PathVariable Long id, @RequestParam Boolean status) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            String message = null;
            if (status) {
                message = "activated";
            } else {
                message = "deactivated";
            }
            responseDTO = new ResponseDTO("Region " + message + " successfully", true, regionService.enableOrDisable(id, status));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @GetMapping("allActive")
    public ResponseEntity<Object> allActive() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Region fetched successfully", true, regionService.allActive());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }
    @GetMapping("allIsActive")
    public ResponseEntity<Object> allIsActive() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Active Region fetched successfully", true, regionService.allIsActive());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping("/filterByPagination")
    public ResponseEntity<Object> getByPaginationAndSpecification
            (@RequestParam(required = false, defaultValue = "updatedTimeStamp") String column,
             @RequestParam(required = false, defaultValue = "1") Integer page,
             @RequestParam(required = false, defaultValue = "10") Integer size,
             @RequestParam(required = false, defaultValue = "false") Boolean asc,
             @RequestBody SpecificationDTO specificationDTO)
    {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Region fetched successfully", true, regionService.getByPaginationAndSpecification(column, page, size, asc, specificationDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
