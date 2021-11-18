package com.costcommission.service;

import com.costcommission.dto.CategoryDTO;
import com.costcommission.entity.Category;
import com.costcommission.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    public List<CategoryDTO> allActive() throws Exception {
        try {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setAmbiguityIgnored(true);
            List<Category> categories = categoryRepository.findAll();
            Type listType = new TypeToken<List<CategoryDTO>>() {
            }.getType();
            return mapper.map(categories, listType);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
