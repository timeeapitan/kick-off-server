package com.backend.service.impl;

import com.backend.model.Position;
import com.backend.repository.PositionRepository;
import com.backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public Position getPosition(Integer id) {
        return positionRepository.getPositionByUserId(id);
    }
}
