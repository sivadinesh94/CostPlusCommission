package com.costcommission.service;

import com.costcommission.dto.RoleDTO;
import com.costcommission.entity.Role;
import com.costcommission.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public RoleDTO findRoleById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Role role = roleRepository.findRoleById(id);
        if(role == null)
            return  null;
        else
            return modelMapper.map(role, RoleDTO.class);
    }

    public List<RoleDTO> findAllRole() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        Type listType = new TypeToken<List<RoleDTO>>() {
        }.getType();
        return modelMapper.map(roleRepository.findAll(), listType);
    }

}
