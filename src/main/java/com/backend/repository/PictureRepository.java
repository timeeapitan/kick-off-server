package com.backend.repository;

import com.backend.model.PictureSrc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PictureRepository extends JpaRepository<PictureSrc, Integer> {

    @Query(value = "SELECT id FROM Picture p WHERE p.path = :path", nativeQuery = true)
    Integer getPictureIdByPath(@Param("path") String path);

    @Query(value = "SELECT * FROM Picture p WHERE p.id = :picture_id", nativeQuery = true)
    PictureSrc getPictureSrcById(@Param("picture_id") Integer pictureId);
}
