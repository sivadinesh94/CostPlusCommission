package com.costcommission.service;

import com.costcommission.dto.ClusterDTO;
import com.costcommission.dto.PaginationResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.entity.Cluster;
import com.costcommission.entity.Region;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.jpaspecification.ClusterSpecification;
import com.costcommission.repository.ClusterRepository;
import com.costcommission.repository.RegionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClusterService {

    @Autowired
    private ClusterRepository clusterRepository;

    @Autowired
    private RegionRepository regionRepository;

    public ClusterDTO create(ClusterDTO clusterDTO) throws MasterDataAlreadyExistException {
        Long countByCode = clusterRepository.countByCode(clusterDTO.getCode().trim().toLowerCase());
        Long countByName = clusterRepository.countByName(clusterDTO.getName().trim().toLowerCase());
        if (countByCode > 0) {
            throw new MasterDataAlreadyExistException("Cluster code already exists");
        } else if (countByName > 0) {
            throw new MasterDataAlreadyExistException("Cluster already exists");
        } else {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            Cluster cluster = modelMapper.map(clusterDTO, Cluster.class);
            cluster.setRegion(regionRepository.findRegionById(clusterDTO.getRegionId()));
            return modelMapper.map(clusterRepository.save(cluster), ClusterDTO.class);
        }
    }

    public ClusterDTO update(ClusterDTO clusterDTO) throws MasterDataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        long countByCode = clusterRepository.countByIdAndCode(clusterDTO.getCode().toLowerCase(), clusterDTO.getId());
        long countByName = clusterRepository.countByIdAndName(clusterDTO.getName().toLowerCase(), clusterDTO.getId());
        if (countByCode > 0) {
            throw new MasterDataAlreadyExistException("Cluster code already exists");
        } else if (countByName > 0) {
            throw new MasterDataAlreadyExistException("Cluster already exists");
        } else {
            Cluster cluster = modelMapper.map(clusterDTO, Cluster.class);
            Region region = regionRepository.findRegionById(clusterDTO.getRegionId());
            cluster.setRegion(region);
            return modelMapper.map(clusterRepository.save(cluster), ClusterDTO.class);

        }
    }

    public int enableOrDisable(Long id, Boolean status) throws Exception {
        try {
            return clusterRepository.enableOrDisable(status, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<ClusterDTO> allActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Cluster> clusters = clusterRepository.allActive();
            List<ClusterDTO> clusterDTOS = new ArrayList<>();
            for(Cluster cluster : clusters) {
                ClusterDTO clusterDTO = mapper.map(cluster, ClusterDTO.class);
                clusterDTO.setRegionId(cluster.getRegion().getId());
                clusterDTO.setRegionName(cluster.getRegion().getName());
                clusterDTOS.add(clusterDTO);
            }
            return clusterDTOS;
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
        ClusterSpecification spec = new ClusterSpecification(specificationDTO);
        Page<Cluster> clusters = clusterRepository.findAll(spec, pageable);
        List<ClusterDTO> clusterDTOS = new ArrayList<>();
        for(Cluster cluster : clusters) {
            ClusterDTO clusterDTO = modelMapper.map(cluster, ClusterDTO.class);
            clusterDTO.setRegionId(cluster.getRegion().getId());
            clusterDTO.setRegionName(cluster.getRegion().getName());
            clusterDTOS.add(clusterDTO);
        }
        paginationResponseDTO.setTotalSize(clusters.getTotalElements());
        paginationResponseDTO.setData(clusterDTOS);
        return paginationResponseDTO;
    }

    public List<ClusterDTO> allIsActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Cluster> clusters = clusterRepository.findClusterByIsActive(true);
            List<ClusterDTO> clusterDTOS = new ArrayList<>();
            for(Cluster cluster : clusters) {
                ClusterDTO clusterDTO = mapper.map(cluster, ClusterDTO.class);
                clusterDTO.setRegionId(cluster.getRegion().getId());
                clusterDTO.setRegionName(cluster.getRegion().getName());
                clusterDTOS.add(clusterDTO);
            }
            return clusterDTOS;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}

