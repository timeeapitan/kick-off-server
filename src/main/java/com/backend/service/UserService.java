package com.backend.service;

import com.backend.model.User;
import com.backend.dto.BodyStructureDTO;
import com.backend.dto.PositionDTO;
import com.backend.dto.PictureDTO;

import java.util.List;

public interface UserService {
    String saveUser(User user);

    List<User> getAllUsers();

    User getUser(Integer id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    Integer getIdByEmail(String email);

    void deleteUser(Integer id);

    String uploadProfilePicture(PictureDTO picture);

    String updateProfilePicture(PictureDTO picture);

    String updateTableProfilePicture(PictureDTO pictureDTO);

    String getProfilePicture(Integer id);

    String saveBodyStructure(BodyStructureDTO modifyBodyStructure);

    String updateBodyStructure(BodyStructureDTO modifyBodyStructure);

    String savePosition(PositionDTO positionDTO);

    String saveTablePosition(PositionDTO positionDTO);

    String updatePosition(PositionDTO positionDTO);

    User updateUser(Integer id, User newUser);

    User getTeamAdmin(Integer teamId);
}
