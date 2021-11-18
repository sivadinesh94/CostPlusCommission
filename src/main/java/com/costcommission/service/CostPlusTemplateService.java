package com.costcommission.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CostPlusTemplateService {

    @Autowired
    private CostPlusTemplateR11Service costPlusTemplateR11Service;

    @Autowired
    private CostPlusTemplateR12Service costPlusTemplateR12Service;

    public void generate() throws IOException {
        costPlusTemplateR11Service.generate();
    }

}