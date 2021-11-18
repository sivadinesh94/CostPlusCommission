package com.costcommission.service;

import com.costcommission.dto.AgencyDTO;
import com.costcommission.dto.PaginationResponseDTO;
import com.costcommission.dto.SpecificationDTO;
import com.costcommission.entity.Agency;
import com.costcommission.entity.Cluster;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.jpaspecification.AgencySpecification;
import com.costcommission.repository.AgencyRepository;
import com.costcommission.repository.ClusterRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
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
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private ClusterRepository clusterRepository;

    public List<AgencyDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Type listType = new TypeToken<List<AgencyDTO>>() {
        }.getType();
        return modelMapper.map(agencyRepository.findAll(), listType);
    }


    public AgencyDTO create(AgencyDTO agencyDTO) throws MasterDataAlreadyExistException, RecordNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Long countByCode = agencyRepository.countCode(agencyDTO.getCode());
        Long countByName = agencyRepository.countName(agencyDTO.getName());
        if (countByCode > 0) {
            throw new MasterDataAlreadyExistException("Agency code already exists");
        } else if (countByName > 0) {
            throw new MasterDataAlreadyExistException("Agency already exists");
        } else {
            Agency agency = modelMapper.map(agencyDTO, Agency.class);
            Cluster cluster = clusterRepository.findById(agencyDTO.getClusterId());
            agency.setCluster(cluster);
            return modelMapper.map(agencyRepository.save(agency), AgencyDTO.class);
        }
    }

    public AgencyDTO update(AgencyDTO agencyDTO) throws MasterDataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        long countByCode = agencyRepository.countByIdAndCode(agencyDTO.getCode().toLowerCase(), agencyDTO.getId());
        long countByName = agencyRepository.countByIdAndName(agencyDTO.getName().toLowerCase(), agencyDTO.getId());
        if (countByCode > 0) {
            throw new MasterDataAlreadyExistException("Cluster code already exists");
        } else if (countByName > 0) {
            throw new MasterDataAlreadyExistException("Cluster already exists");
        } else {
            Agency agency = modelMapper.map(agencyDTO, Agency.class);
            Cluster cluster = clusterRepository.findById(agencyDTO.getClusterId());
            agency.setCluster(cluster);
            return modelMapper.map(agencyRepository.save(agency), AgencyDTO.class);
        }
    }

    public int enableOrDisable(Long id, Boolean status) throws Exception {
        try {
            return agencyRepository.inActivate(status, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<AgencyDTO> allActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Agency> agencies = agencyRepository.allActive();
            List<AgencyDTO> agencyDTOS = new ArrayList<>();
            for(Agency agency : agencies) {
                AgencyDTO agencyDTO = mapper.map(agency, AgencyDTO.class);
                agencyDTO.setClusterId(agency.getCluster().getId());
                agencyDTO.setClusterName(agency.getCluster().getName());
                agencyDTOS.add(agencyDTO);
            }
            return agencyDTOS;
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
        AgencySpecification spec = new AgencySpecification(specificationDTO);
        Page<Agency> agencies = agencyRepository.findAll(spec, pageable);
        List<AgencyDTO> agencyDTOS = new ArrayList<>();
        for(Agency agency : agencies) {
            AgencyDTO agencyDTO = modelMapper.map(agency, AgencyDTO.class);
            agencyDTO.setClusterId(agency.getCluster().getId());
            agencyDTO.setClusterName(agency.getCluster().getName());
            agencyDTOS.add(agencyDTO);
        }
        paginationResponseDTO.setTotalSize(agencies.getTotalElements());
        paginationResponseDTO.setData(agencyDTOS);
        return paginationResponseDTO;
    }

}
