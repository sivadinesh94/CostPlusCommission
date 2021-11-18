package com.costcommission.controller;

import com.costcommission.dto.ClusterDTO;
import com.costcommission.dto.RegionDTO;
import com.costcommission.dto.ResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cluster")
public class ClusterController {

    @Autowired
    private ClusterService clusterService;

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
            responseDTO = new ResponseDTO("Cluster fetched successfully", true, clusterService.getByPaginationAndSpecification(column, page, size, asc, specificationDTO));
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ClusterDTO clusterDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cluster created successfully", true, clusterService.create(clusterDTO));
            httpStatus = HttpStatus.OK;
        } catch (MasterDataAlreadyExistException me) {
            me.printStackTrace();
            responseDTO = new ResponseDTO(me.getMessage(), false, null);
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody ClusterDTO clusterDTO) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cluster Updated successfully", true, clusterService.update(clusterDTO));
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
            responseDTO = new ResponseDTO("Cluster " + message +" successfully", true, clusterService.enableOrDisable(id, status));
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

    @GetMapping("/allActive")
    public ResponseEntity<Object> allActive() {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = new ResponseDTO("Cluster fetched successfully", true, clusterService.allActive());
            httpStatus = HttpStatus.OK;
        }catch (Exception e) {
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
            responseDTO = new ResponseDTO("Active Cluster fetched successfully", true, clusterService.allIsActive());
            httpStatus = HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            responseDTO = new ResponseDTO(e.getMessage(), false, null);
        }
        return new ResponseEntity<>(responseDTO, httpStatus);
    }

}