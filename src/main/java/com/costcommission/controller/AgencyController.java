package com.costcommission.controller;

import com.costcommission.dto.AgencyDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Agency fetched successfully", true, agencyService.findAll());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody AgencyDTO agencyDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Agency created successfully", true, agencyService.create(agencyDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody AgencyDTO agencyDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Agency Updated successfully", true, agencyService.update(agencyDTO));
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
            if(status) {
                message = "activated";
            } else {
                message = "deactivated";
            }
            responseDTO = new ResponseDTO("Agency Inactivated successfully", true, agencyService.enableOrDisable(id, status));
            httpStatus = HttpStatus.OK;
        } catch (RecordNotFoundException re) {
            responseDTO = new ResponseDTO(re.getMessage(), false, null);
        }catch (Exception e) {
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
            responseDTO = new ResponseDTO("Agency fetched successfully", true, agencyService.allActive());
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
            responseDTO = new ResponseDTO("Agency fetched successfully", true, agencyService.getByPaginationAndSpecification(column, page, size, asc, specificationDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
