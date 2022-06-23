package com.backend.repository;

import com.backend.model.BodyStructure;
import com.backend.model.Position;
import com.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM User u WHERE u.username = :username AND u.password = :password", nativeQuery = true)
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM User u WHERE u.email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email") String email);

    @Query(value = "SELECT user_id FROM User u WHERE u.email = :email", nativeQuery = true)
    Integer getIdByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM User u WHERE u.email = :email", nativeQuery = true)
    User findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM User u WHERE u.username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM User u WHERE u.user_id = :id", nativeQuery = true)
    User findByUserId(@Param("id") Integer id);

    @Query(value = "SELECT picture_id FROM User u WHERE u.user_id = :id", nativeQuery = true)
    String getProfilePictureId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User u JOIN Picture p ON picture_id = id SET p.path = :path WHERE u.user_id = :user_id", nativeQuery = true)
    void updateUserProfilePicture(@Param("user_id") Integer userId, @Param("path") String picturePath);

    @Query(value = "SELECT path FROM User u JOIN Picture p ON picture_id = p.id WHERE u.user_id = :user_id", nativeQuery = true)
    String getProfilePictureById(@Param("user_id") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE body_structure bs JOIN User u ON id = body_structure_id SET foot = :foot, height = :height, weight = :weight WHERE u.user_id = :user_id", nativeQuery = true)
    void updateBodyStructure(@Param("foot") String foot, @Param("height") Double height, @Param("weight") Double weight, @Param("user_id") Integer userId);

    @Query(value = "SELECT body_structure_id FROM User WHERE user_id = :user_id", nativeQuery = true)
    BodyStructure getBodyStructureByUserId(@Param("user_id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE position p JOIN User u ON id = position_id SET attacker = :attacker, defender = :defender, goalkeeper = :goalkeeper, midfielder = :midfielder WHERE u.user_id = :user_id", nativeQuery = true)
    void updatePosition(@Param("attacker") Integer attacker, @Param("defender") Integer defender, @Param("goalkeeper") Integer goalkeeper, @Param("midfielder") Integer midfielder, @Param("user_id") Integer userId);

    @Query(value = "SELECT position_id FROM User WHERE user_id = :user_id", nativeQuery = true)
    Position getPositionById(@Param("user_id") Integer id);

    @Query(value = "SELECT * FROM User u WHERE user_id IN (SELECT admin_id FROM Team t WHERE id = :teamId)", nativeQuery = true)
    User getTeamAdmin(@Param("teamId") Integer id);
}
