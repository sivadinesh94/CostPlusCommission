package com.costcommission.controller;

import com.costcommission.dto.CoaDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.service.COAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coa")
public class COAController {

    @Autowired
    private COAService coaService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("COA fetched successfully", true, coaService.findAll());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CoaDTO coaDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Chart Of Account created successfully", true, coaService.create(coaDTO));
            httpStatus = HttpStatus.OK;
        } catch (MasterDataAlreadyExistException me) {
            me.printStackTrace();
            responseDTO = new ResponseDTO(me.getMessage(), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO("Chart Of Account creation failed", false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody CoaDTO coaDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Chart Of Account Updated successfully", true, coaService.update(coaDTO));
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO("Chart Of Account update failed", false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping("/{id}/enableOrDisable")
    public ResponseEntity<Object> enableOrDisable(@PathVariable("id") Long id, @RequestParam Boolean status) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Chart Of Account Inactivated successfully", true, coaService.enableOrDisable(id, status));
            httpStatus = HttpStatus.OK;
        } catch (RecordNotFoundException re) {
            responseDTO = new ResponseDTO(re.getMessage(), false, null);
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}
