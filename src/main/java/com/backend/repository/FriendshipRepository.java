package com.backend.repository;

import com.backend.model.Friendship;
import com.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    boolean existsByFirstUserAndSecondUser(User first, User second);

    boolean existsBySecondUserAndFirstUser(User first, User second);

    boolean existsByFirstUserIdAndSecondUserId(int firstUserId, int secondUserId);

    boolean existsBySecondUserIdAndFirstUserId(int firstUserId, int secondUserId);

    List<Friendship> findByFirstUser(User user);

    List<Friendship> findBySecondUser(User user);

    @Query(value = "SELECT created_date FROM friendship f WHERE f.first_user_id = :firstId AND f.second_user_id = :secondId", nativeQuery = true)
    Date getCreatedDate(@Param("firstId") int firstUserId, @Param("secondId") int secondUserId);

    @Transactional
    @Modifying
    @Query(value = "DELETE from friendship f WHERE f.first_user_id = :firstId AND f.second_user_id = :secondId", nativeQuery = true)
    void deleteFriendship(@Param("firstId") int firstUserId, @Param("secondId") int secondUserId);

    @Query(value = "SELECT * FROM friendship WHERE f.first_user_id = :firstId AND f.second_user_id = :secondId", nativeQuery = true)
    Friendship getFriendshipByFirstUserAndSecondUser(@Param("firstId") int firstUserId, @Param("secondId") int secondUserId);
}
