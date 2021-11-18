package com.costcommission.service;

import com.costcommission.dto.PaginationResponseDTO;
import com.costcommission.dto.RegionDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.entity.Region;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.jpaspecification.RegionSpecification;
import com.costcommission.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;


    public RegionDTO create(RegionDTO regionDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        try {
            Long countCode = regionRepository.countByCode(regionDTO.getCode().trim().toLowerCase());
            Long countName = regionRepository.countByName(regionDTO.getName().trim().toLowerCase());
            if (countCode > 0) {
                throw new MasterDataAlreadyExistException("Region Code already exists");
            } else if (countName > 0) {
                throw new MasterDataAlreadyExistException("Region already exists");
            } else {
                Region region = modelMapper.map(regionDTO, Region.class);
                return modelMapper.map(regionRepository.save(region), RegionDTO.class);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public RegionDTO update(RegionDTO regionDTO) throws MasterDataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Long countByCode = regionRepository.countByIdAndCode(regionDTO.getCode().toLowerCase(), regionDTO.getId());
        Long countByName = regionRepository.countByIdAndName(regionDTO.getName().toLowerCase(), regionDTO.getId());
        if (countByCode > 0) {
            throw new MasterDataAlreadyExistException("Region code already exists");
        } else if (countByName > 0) {
            throw new MasterDataAlreadyExistException("Region already exists");
        } else {
            Region existRegion = regionRepository.findRegionById(regionDTO.getId());
            Region region = modelMapper.map(regionDTO, Region.class);
            region.setCreatedTimeStamp(existRegion.getCreatedTimeStamp());
            return modelMapper.map(regionRepository.save(region), RegionDTO.class);
        }
    }

    public int enableOrDisable(Long id, Boolean status) throws Exception {
        try {
            return regionRepository.enableOrDisable(status, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<RegionDTO> allActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Region> regions = regionRepository.allActive();
            Type listType = new TypeToken<List<RegionDTO>>() {
            }.getType();
            return mapper.map(regions, listType);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<RegionDTO> allIsActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Region> regions = regionRepository.findRegionByIsActive(true);
            Type listType = new TypeToken<List<RegionDTO>>() {
            }.getType();
            return mapper.map(regions, listType);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public PaginationResponseDTO getByPaginationAndSpecification(String column, int page, int size, Boolean asc, SpecificationDTO specificationDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        PaginationResponseDTO paginationResponseDTO = new PaginationResponseDTO();
        Sort.Direction sort = Sort.Direction.DESC;
        if(asc) {
            sort = Sort.Direction.ASC;
        }
        Pageable pageable = PageRequest.of(page, size, sort, column);
        RegionSpecification spec = new RegionSpecification(specificationDTO);
        Page<Region> results = regionRepository.findAll( spec, pageable);
        Type listType = new TypeToken<List<RegionDTO>>() {
        }.getType();
        paginationResponseDTO.setTotalSize(results.getTotalElements());
        paginationResponseDTO.setData(modelMapper.map(results.getContent(), listType));
        return paginationResponseDTO;
    }

}
