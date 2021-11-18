package com.costcommission.service;

import com.costcommission.dto.DesignationDTO;
import com.costcommission.entity.Designation;
import com.costcommission.repository.DesignationRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    public DesignationDTO findDesignationById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Designation designation = designationRepository.findDesignationById(id);
        if(designation == null)
            return  null;
        else
            return modelMapper.map(designation, DesignationDTO.class);
    }

    public List<DesignationDTO> findAllDesignation() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Type listType = new TypeToken<List<DesignationDTO>>() {
        }.getType();
        return modelMapper.map(designationRepository.findAll(), listType);
    }

}
