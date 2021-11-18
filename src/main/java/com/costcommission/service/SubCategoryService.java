package com.costcommission.service;

import com.costcommission.dto.AgencyDTO;
import com.costcommission.dto.SubCategoryDTO;
import com.costcommission.entity.Agency;
import com.costcommission.entity.SubCategory;
import com.costcommission.repository.SubCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public List<SubCategoryDTO> allActiveByCategoryId(Long categoryId) throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<SubCategory> subCategories = subCategoryRepository.findByCategoryId(categoryId);
            List<SubCategoryDTO> subCategoryDTOS = new ArrayList<>();
            for (SubCategory subCategory : subCategories) {
                SubCategoryDTO subCategoryDTO = mapper.map(subCategory, SubCategoryDTO.class);
                subCategoryDTO.setCategoryId(subCategory.getCategory().getId());
                subCategoryDTO.setCategoryName(subCategory.getCategory().getName());
                subCategoryDTOS.add(subCategoryDTO);
            }
            return subCategoryDTOS;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public SubCategoryDTO findById(Long id) throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            SubCategory subCategory = subCategoryRepository.findById(id);
            SubCategoryDTO subCategoryDTO = mapper.map(subCategory, SubCategoryDTO.class);
            subCategoryDTO.setCategoryId(subCategory.getCategory().getId());
            subCategoryDTO.setCategoryName(subCategory.getCategory().getName());
            return subCategoryDTO;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
