package com.backend.repository;

import com.backend.model.Match;
import com.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(value = "SELECT * FROM matches WHERE admin_id = :adminId", nativeQuery = true)
    List<Match> getAllMatchesByAdmin(@Param("adminId") Integer adminId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM matches WHERE id = :id", nativeQuery = true)
    void deleteMatchById(@Param("id") Integer id);

    @Query(value = "SELECT first_name, last_name, username, table_picture_src, table_position  FROM User u JOIN user_match um ON u.user_id = um.user_id WHERE match_id = :matchId", nativeQuery = true)
    List<Object> getAllPlayersFromMatch(@Param("matchId") Integer matchId);
}
