package com.backend.repository;

import com.backend.model.Team;
import com.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Team t WHERE t.name = :name", nativeQuery = true)
    void deleteUserByTeam(String name);

    @Query(value = "SELECT * FROM Team t WHERE t.id = :id", nativeQuery = true)
    Team findByTeamId(@Param("id") Integer id);

    List<Team> findAllByAdmin_Id(Integer adminId);

    @Query(value = "SELECT t.* FROM Team t JOIN user_team ut ON t.id = ut.team_id JOIN User u ON u.user_id = ut.user_id WHERE ut.user_id = :userId", nativeQuery = true)
    List<Team> getTeamsByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM Team t WHERE t.name = :name", nativeQuery = true)
    Team findTeamByName(@Param("name") String name);
}
