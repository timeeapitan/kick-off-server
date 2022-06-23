package com.backend.controller;

import com.backend.model.BodyStructure;
import com.backend.service.BodyStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/body-structure")
@CrossOrigin
public class BodyStructureController {

    @Autowired
    private BodyStructureService bodyStructureService;

    @GetMapping("/get-body-structure")
    public ResponseEntity<BodyStructure> getBodyStructure(@RequestParam Integer id) {
        try {
            BodyStructure bodyStructure = bodyStructureService.getBodyStructure(id);
            if(bodyStructure == null){
                bodyStructure = BodyStructure.builder().id(-1).build();
            }
            return new ResponseEntity<>(bodyStructure, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
