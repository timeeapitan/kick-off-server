package com.backend.controller;

import com.backend.model.Position;
import com.backend.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/position")
@CrossOrigin
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/get-position")
    public ResponseEntity<Position> getPosition(@RequestParam Integer id) {
        try {
            Position position = positionService.getPosition(id);
            if(position == null){
                position = Position.builder().id(-1).build();
            }
            return new ResponseEntity<>(position, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
