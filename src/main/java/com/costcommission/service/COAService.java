package com.costcommission.service;

import com.costcommission.dto.CoaDTO;
import com.costcommission.entity.COA;
import com.costcommission.exception.MasterDataAlreadyExistException;
import com.costcommission.exception.RecordNotFoundException;
import com.costcommission.repository.COARepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

@Service
public class COAService {

    @Autowired
    private COARepository coaRepository;

    public List<CoaDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Type listType = new TypeToken<List<CoaDTO>>() {
        }.getType();
        return modelMapper.map(coaRepository.findAll(), listType);
    }

    public CoaDTO create(CoaDTO coaDTO) throws MasterDataAlreadyExistException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        Long count = coaRepository.count(coaDTO.getNatureCode().trim().toLowerCase());
        if (count > 0) {
            throw new MasterDataAlreadyExistException("Chart Of Accounts already exists");
        } else {
            COA coa = modelMapper.map(coaDTO, COA.class);
            return modelMapper.map(coaRepository.save(coa), CoaDTO.class);
        }
    }

    public CoaDTO update(CoaDTO coaDTO) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        COA coa = modelMapper.map(coaDTO, COA.class);
        return modelMapper.map(coaRepository.update(coa.getNatureDesc(), coa.getDescription(), coa.getSafranpl()
                , coa.getSafranplDetails(), coa.getAgencyBudgetCode(), coa.getAgencyBudgetLine()
                , coa.getMandatoryAllocPerCC(), coa.getUnallocatedExpenses(), coa.getCostCenterAllocation()
                , coa.getNatureNotRelevant(), coa.getRationale(), coa.getCostPlusMatrix(), coa.getDescriptionEnglish()
                , coa.getIsActive(), coa.getNatureCode()), CoaDTO.class);
    }

    public int enableOrDisable(Long id, Boolean status) throws Exception {
        try {
            return coaRepository.enableOrDisable(status, id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
