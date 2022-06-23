package com.backend.service.impl;

import com.backend.dto.BodyStructureDTO;
import com.backend.dto.PictureDTO;
import com.backend.dto.PositionDTO;
import com.backend.model.BodyStructure;
import com.backend.model.PictureSrc;
import com.backend.model.Position;
import com.backend.model.User;
import com.backend.repository.BodyStructureRepository;
import com.backend.repository.PictureRepository;
import com.backend.repository.PositionRepository;
import com.backend.repository.UserRepository;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private BodyStructureRepository bodyStructureRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Override
    public String saveUser(User user) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (matcher.matches()) {
            userRepository.save(user);
            return "New user added!";
        }

        System.out.println(user.getEmail() + ": " + matcher.matches());
        return "User could not be added!";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Integer getIdByEmail(String email) {
        return userRepository.getIdByEmail(email);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public String uploadProfilePicture(PictureDTO picture) {
        if (userRepository.getProfilePictureId(picture.getId()) == null) {
            String description = "profile picture for user " + picture.getId();
            PictureSrc pic = PictureSrc.builder().description(description).path(picture.getSrc()).build();
            pictureRepository.save(pic);
            Integer pictureId = pictureRepository.getPictureIdByPath(picture.getSrc());

            User user = userRepository.findByUserId(picture.getId());
            user.setPictureSrc(pictureRepository.getPictureSrcById(pictureId));
            userRepository.save(user);
        }
        return "Picture uploaded successfully!";
    }

    @Override
    public String updateProfilePicture(PictureDTO picture) {
        if (userRepository.getProfilePictureId(picture.getId()) != null) {
            userRepository.updateUserProfilePicture(picture.getId(), picture.getSrc());
        }
        return "Picture updated successfully!";
    }

    @Override
    public String getProfilePicture(Integer id) {
        return userRepository.getProfilePictureById(id);
    }

    @Override
    public String saveBodyStructure(BodyStructureDTO modifyBodyStructure) {
        User user = userRepository.getById(modifyBodyStructure.getId());
        if (user.getBodyStructure() == null) {
            String foot = modifyBodyStructure.getBodyStructure().getFoot();
            Double height = modifyBodyStructure.getBodyStructure().getHeight();
            Double weight = modifyBodyStructure.getBodyStructure().getWeight();

            BodyStructure bodyStructure = BodyStructure.builder().foot(foot).height(height).weight(weight).build();
            BodyStructure bodyFromDB = bodyStructureRepository.save(bodyStructure);
            Integer bodyStructureId = bodyFromDB.getId();

            user.setBodyStructure(bodyStructureRepository.getById(bodyStructureId));
            userRepository.save(user);
            return "Body structure saved successfully!";
        }

        return "Cannot save body structure!";
    }

    @Override
    public String updateBodyStructure(BodyStructureDTO bodyStructure) {
        userRepository.updateBodyStructure(bodyStructure.getBodyStructure().getFoot(),
                bodyStructure.getBodyStructure().getHeight(),
                bodyStructure.getBodyStructure().getWeight(),
                bodyStructure.getId());

        return "Body structure updated successfully!";
    }

    @Override
    public String savePosition(PositionDTO positionDTO) {
        User user = userRepository.getById(positionDTO.getId());
        if (user.getPosition() == null) {
            Integer goalkeeper = positionDTO.getPosition().getGoalkeeper();
            Integer midfielder = positionDTO.getPosition().getMidfielder();
            Integer defender = positionDTO.getPosition().getDefender();
            Integer attacker = positionDTO.getPosition().getAttacker();

            Position position = Position.builder()
                    .goalkeeper(goalkeeper)
                    .midfielder(midfielder)
                    .defender(defender)
                    .attacker(attacker)
                    .build();

            Position positionFromDB = positionRepository.save(position);
            Integer positionId = positionFromDB.getId();

            user.setPosition(positionRepository.getById(positionId));
            userRepository.save(user);

            return "Position saved successfully!";
        }

        return "Cannot save body structure!";
    }

    @Override
    public String updatePosition(PositionDTO positionDTO) {
        userRepository.updatePosition(positionDTO.getPosition().getAttacker(),
                positionDTO.getPosition().getDefender(),
                positionDTO.getPosition().getGoalkeeper(),
                positionDTO.getPosition().getMidfielder(),
                positionDTO.getId());

        return "PositionService updated successfully!";
    }

    @Override
    public User updateUser(Integer id, User newUser) {
        User user = userRepository.findByUserId(id);
        user.setUsername(newUser.getUsername());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setDateOfBirth(newUser.getDateOfBirth());
        user.setCountry(newUser.getCountry());

        return userRepository.save(user);
    }

    @Override
    public User getTeamAdmin(Integer teamId) {
        return userRepository.getTeamAdmin(teamId);
    }

    @Override
    public String updateTableProfilePicture(PictureDTO pictureDTO) {
        User user = userRepository.findByUserId(pictureDTO.getId());
        user.setTablePictureSrc(pictureDTO.getSrc());
        userRepository.save(user);
        return "Picture updated successfully!";
    }

    @Override
    public String saveTablePosition(PositionDTO positionDTO) {
        User user = userRepository.findByUserId(positionDTO.getId());
        if (positionDTO.getPosition().getAttacker() == 1) {
            user.setTablePosition("attacker");
        } else if (positionDTO.getPosition().getDefender() == 1) {
            user.setTablePosition("defender");
        } else if (positionDTO.getPosition().getGoalkeeper() == 1) {
            user.setTablePosition("goalkeeper");
        } else if (positionDTO.getPosition().getMidfielder() == 1) {
            user.setTablePosition("midfielder");
        }
        userRepository.save(user);
        return "Position updated successfully!";
    }
}
