package com.backend.controller;

import com.backend.model.User;
import com.backend.repository.UserRepository;
import com.backend.dto.BodyStructureDTO;
import com.backend.dto.PositionDTO;
import com.backend.dto.PictureDTO;
import com.backend.dto.UserDTO;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/getAll")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById")
    public ResponseEntity<User> get(@RequestParam Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<User> get(@RequestParam String username) {
        try {
            User user = userService.getUserByUsername(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existingStudent = userService.getUser(id);
            existingStudent.setFirstName(user.getFirstName());
            existingStudent.setLastName(user.getLastName());
            existingStudent.setEmail(user.getEmail());
            existingStudent.setUsername(user.getUsername());
            existingStudent.setPassword(user.getPassword());
            existingStudent.setDateOfBirth(user.getDateOfBirth());
            userService.saveUser(existingStudent);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "Deleted user with id " + id + ".";
    }

    @GetMapping("/getId")
    public Integer getIdByEmail(@RequestParam String email) {
        return userService.getIdByEmail(email);
    }

    @GetMapping("/getUserByEmail")
    public User getUser(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity getUserByUsername(@RequestParam String username) {
        try{
            User user = userService.getUserByUsername(username);
            if(user == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("No user found with username " + username);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.getUserByUsername(username));
        } catch(NoSuchElementException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No user found with username " + username);
        }
    }

    @PutMapping("/updateUser")
    public User updateUserById(@RequestBody UserDTO userDTO) {
        return userService.updateUser(userDTO.getId(), userDTO.getUser());
    }

    @PostMapping("/uploadPicture")
    public String uploadPicture(@RequestBody PictureDTO picture) {
        return userService.uploadProfilePicture(picture);
    }

    @PutMapping("/updatePicture")
    public String updatePicture(@RequestBody PictureDTO picture) {
        return userService.updateProfilePicture(picture);
    }

    @GetMapping("/getProfilePicture")
    public String getProfilePicture(@RequestParam Integer id) {
        return userService.getProfilePicture(id);
    }

    @PutMapping("/updateBodyStructure")
    public String updateBodyStructure(@RequestBody BodyStructureDTO modifyBodyStructure) {
        return userService.updateBodyStructure(modifyBodyStructure);
    }

    @PostMapping("/saveBodyStructure")
    public String saveBodyStructure(@RequestBody BodyStructureDTO modifyBodyStructure) {
        return userService.saveBodyStructure(modifyBodyStructure);
    }

    @PutMapping("/updatePosition")
    public String updatePosition(@RequestBody PositionDTO positionDTO) {
        return userService.updatePosition(positionDTO);
    }

    @PostMapping("/savePosition")
    public String savePosition(@RequestBody PositionDTO positionDTO) {
        return userService.savePosition(positionDTO);
    }

    @GetMapping("/getTeamAdmin")
    public User getTeamAdmin(@RequestParam Integer teamId){
        return userService.getTeamAdmin(teamId);
    }

    @PutMapping("/updateTableProfilePicture")
    public String updateTableProfilePicture(@RequestBody PictureDTO pictureDTO){
        return userService.updateTableProfilePicture(pictureDTO);
    }

    @PutMapping("/updateTablePosition")
    public String updateTableProfilePosition(@RequestBody PositionDTO positionDTO){
        return userService.saveTablePosition(positionDTO);
    }
}


