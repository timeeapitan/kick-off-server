package com.backend.repository;

import com.backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Query(value = "SELECT * FROM Chat c WHERE c.team_id = :team_id", nativeQuery = true)
    List<Chat> getAllMessagesById(@Param("team_id") Integer team_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Chat c where c.team_id = :teamId", nativeQuery = true)
    void deleteChatFromTeam(@Param("teamId") Integer teamId);
}
