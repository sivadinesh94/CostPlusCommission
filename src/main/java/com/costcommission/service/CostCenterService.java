package com.costcommission.service;

import com.costcommission.dto.*;
import com.costcommission.entity.Agency;
import com.costcommission.entity.CostCenter;
import com.costcommission.entity.SubCategory;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.jpaspecification.AgencySpecification;
import com.costcommission.jpaspecification.CostCenterSpecification;
import com.costcommission.repository.CategoryRepository;
import com.costcommission.repository.CostCenterRepository;
import com.costcommission.repository.SubCategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class CostCenterService {

    @Autowired
    private CostCenterRepository costCenterRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SubCategoryService subCategoryService;

    public List<CostCenter> getAllCostCenter() {
        List<CostCenter> listCostCenter = costCenterRepository.findAllCostCenter();
        return listCostCenter;
    }

    public List<CostCenterDTO> findAllCostCenter(String type) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Type listType = new TypeToken<List<CostCenterDTO>>() {
        }.getType();
        return modelMapper.map(costCenterRepository.findCostCenterByType(type), listType);
    }

    public CostCenterDTO create(CostCenterDTO costCenterDTO) throws Exception {
        try {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            Long count = costCenterRepository.count(costCenterDTO.getCode().trim().toLowerCase(), costCenterDTO.getName().trim().toLowerCase());
            if (count > 0) {
                throw new MasterDataAlreadyExistException("Cost Center code already exists");
            } else {
                CostCenter costCenter = modelMapper.map(costCenterDTO, CostCenter.class);
                SubCategory subCategory = subCategoryRepository.findById(costCenterDTO.getSubcategoryId());
                costCenter.setSubCategory(subCategory);
                return modelMapper.map(costCenterRepository.save(costCenter), CostCenterDTO.class);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int enableOrDisable(Long id, Boolean status) throws Exception {
        try {
            return costCenterRepository.enableOrDisable(status, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public CostCenterDTO update(CostCenterDTO costCenterDTO) throws MasterDataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CostCenterDTO costCenterName = findByName(costCenterDTO);
        if (costCenterName != null && !costCenterName.getCode().equals(costCenterDTO.getCode())) {
            throw new MasterDataAlreadyExistException("Same Cost Center already exists");
        } else {
            CostCenterDTO savedCostCenter = findByNameOrCode(costCenterDTO);
            if (savedCostCenter.getId().equals(costCenterDTO.getId())) {
                CostCenter costCenter = modelMapper.map(costCenterDTO, CostCenter.class);
                return modelMapper.map(costCenterRepository.update(costCenter.getName(), costCenter.getSequence(),
                        costCenter.getKeyAllocation(), costCenter.getStatus(), costCenter.getCarrierCode(), costCenter.getCarrierName()
                        , costCenter.getId()), CostCenterDTO.class);
            } else {
                throw new MasterDataAlreadyExistException("Same Cost Center already exists");
            }
        }
    }

    private CostCenterDTO findByNameOrCode(CostCenterDTO costCenterDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CostCenter costCenter = modelMapper.map(costCenterDTO, CostCenter.class);
        return modelMapper.map(costCenterRepository.findByNameOrCode(costCenterDTO.getName(), costCenterDTO.getCode()), CostCenterDTO.class);
    }

    private CostCenterDTO findByName(CostCenterDTO costCenterDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        CostCenter costCenter = modelMapper.map(costCenterDTO, CostCenter.class);
        costCenter = costCenterRepository.findByName(costCenterDTO.getName().trim());
        if (costCenter == null)
            return null;
        else
            return modelMapper.map(costCenter, CostCenterDTO.class);
    }

    public PaginationResponseDTO getByPaginationAndSpecification(String column, int page, int size, Boolean asc, SpecificationDTO specificationDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
        Sort.Direction sort = Sort.Direction.DESC;
        if (asc) {
            sort = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, sort, column);
        CostCenterSpecification spec = new CostCenterSpecification(specificationDTO);
        Page<CostCenter> costCenters = costCenterRepository.findAll(spec, pageable);
        List<CostCenterDTO> costCenterDTOS = new ArrayList<>();
        for (CostCenter costCenter : costCenters) {
            CostCenterDTO costCenterDTO = modelMapper.map(costCenter, CostCenterDTO.class);
            SubCategory subCategory = subCategoryRepository.findById(costCenter.getSubCategory().getId());
            costCenterDTO.setSubcategoryId(subCategory.getId());
            costCenterDTO.setSubcategoryName(subCategory.getName());
            costCenterDTO.setCategoryId(subCategory.getCategory().getId());
            costCenterDTO.setCategoryName(subCategory.getCategory().getName());
            costCenterDTOS.add(costCenterDTO);
        }
        paginationResponseDTO.setTotalSize(costCenters.getTotalElements());
        paginationResponseDTO.setData(costCenterDTOS);
        return paginationResponseDTO;
    }

}
