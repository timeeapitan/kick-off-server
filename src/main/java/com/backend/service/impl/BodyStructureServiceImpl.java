package com.backend.service.impl;

import com.backend.model.BodyStructure;
import com.backend.repository.BodyStructureRepository;
import com.backend.service.BodyStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BodyStructureServiceImpl implements BodyStructureService {

    @Autowired
    private BodyStructureRepository bodyStructureRepository;

    @Override
    public BodyStructure getBodyStructure(Integer id) {
        return bodyStructureRepository.getBodyStructureByUserId(id);
    }
}
