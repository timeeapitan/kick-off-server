package com.backend.repository;

import com.backend.model.BodyStructure;
import com.backend.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyStructureRepository extends JpaRepository<BodyStructure, Integer> {

    @Query(value = "SELECT * FROM User u JOIN body_structure bs ON body_structure_id = id WHERE user_id = :user_id", nativeQuery = true)
    BodyStructure getBodyStructureByUserId(@Param("user_id") Integer id);
}
