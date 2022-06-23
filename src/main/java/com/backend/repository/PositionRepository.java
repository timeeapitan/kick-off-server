package com.backend.repository;

import com.backend.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    @Query(value = "SELECT * FROM User u JOIN position p ON position_id = id WHERE user_id = :user_id", nativeQuery = true)
    Position getPositionByUserId(@Param("user_id") Integer id);
}
