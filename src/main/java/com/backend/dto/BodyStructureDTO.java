package com.backend.dto;

import com.backend.model.BodyStructure;

public class BodyStructureDTO {
    private Integer id;
    private BodyStructure bodyStructure;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BodyStructure getBodyStructure() {
        return bodyStructure;
    }

    public void setBodyStructure(BodyStructure bodyStructure) {
        this.bodyStructure = bodyStructure;
    }
}
