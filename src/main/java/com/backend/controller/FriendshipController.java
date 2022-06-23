package com.backend.controller;

import com.backend.model.User;
import com.backend.repository.UserRepository;
import com.backend.dto.FriendshipDTO;
import com.backend.service.FriendshipService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/friendship")
@CrossOrigin
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/listFriends")
    public ResponseEntity<Set<User>> getFriends(@RequestParam int id) {
        Set<User> myFriends = friendshipService.getFriends(id);
        System.out.println(myFriends.toString());
        return new ResponseEntity<>(myFriends, HttpStatus.OK);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<String> addUser(@RequestBody FriendshipDTO friendshipRequestBody) throws NullPointerException {
        if (friendshipService.saveFriendship(friendshipRequestBody.getUserId(), friendshipRequestBody.getFriendId())) {
            return new ResponseEntity<>("Friendship added successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Friendship already exists in the database!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getDate")
    public Date getCreatedDate(@RequestParam int firstUserId, @RequestParam int secondUserId) {
        return friendshipService.getDate(firstUserId, secondUserId);
    }

    @DeleteMapping("/deleteFriendship")
    public String deleteFriendship(@RequestParam int firstUserId, @RequestParam int secondUserId) {
        return friendshipService.deleteFriend(firstUserId, secondUserId);
    }

    @GetMapping("/existsFriendship")
    public boolean existsFriendship(@RequestParam int firstUserId, @RequestParam int secondUserId){
        return friendshipService.existsFriendship(firstUserId, secondUserId);
    }

//    @GetMapping("/getFriendship")
//    public Friendship getFriendship(@RequestParam int firstUserId, @RequestParam int secondUserId){
//        return friendshipService.getFriendshipByFirstUserIdAndSecondUserId(firstUserId, secondUserId);
//    }
}
