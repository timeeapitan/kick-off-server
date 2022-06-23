package com.backend.service.impl;

import com.backend.model.Friendship;
import com.backend.model.User;
import com.backend.repository.FriendshipRepository;
import com.backend.service.FriendshipService;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    UserService userService;

    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public boolean saveFriendship(int firstUserId, int secondUserId) {
        User firstUser = userService.getUser(firstUserId);
        User secondUser = userService.getUser(secondUserId);

        User user1 = firstUser;
        User user2 = secondUser;

        if (firstUserId > secondUserId) {
            user1 = secondUser;
            user2 = firstUser;
        }

        if (!(friendshipRepository.existsByFirstUserAndSecondUser(user1, user2)) && !(friendshipRepository.existsBySecondUserAndFirstUser(user1, user2))) {
            Friendship friendship = Friendship.builder()
                    .firstUser(user1)
                    .secondUser(user2)
                    .createdDate(new Date())
                    .build();
            friendshipRepository.save(friendship);
            return true;
        }
        return false;
    }

    @Override
    public Set<User> getFriends(int id) {
        User firstUser = userService.getUser(id);

        List<Friendship> friendsByFirstUser = friendshipRepository.findByFirstUser(firstUser);
        List<Friendship> friendsBySecondUser = friendshipRepository.findBySecondUser(firstUser);

        Set<User> friendUsers = new HashSet<>();

        for (Friendship friendship : friendsByFirstUser) {
            friendUsers.add(userService.getUser(friendship.getSecondUser().getId()));
        }

        for (Friendship friendship : friendsBySecondUser) {
            friendUsers.add(userService.getUser(friendship.getFirstUser().getId()));
        }

        return friendUsers;
    }

    @Override
    public Date getDate(int firstUserId, int secondUserId) {
        return friendshipRepository.getCreatedDate(firstUserId, secondUserId);
    }
//
//    @Override
//    public String deleteFriend(Friendship friendship) {
//        int firstUserId = friendship.getFirstUser().getId();
//        int secondUserId = friendship.getSecondUser().getId();
//        friendshipRepository.deleteFriendship(firstUserId, secondUserId);
//        return "Friendship between " + userService.getUser(firstUserId).getUsername() + " and " + userService.getUser(secondUserId).getUsername() + " was deleted successfully!";
//    }
//
//    @Override
//    public Friendship getFriendshipByFirstUserIdAndSecondUserId(int firstId, int secondId) {
//        return friendshipRepository.getFriendshipByFirstUserAndSecondUser(firstId, secondId);
//    }


    @Override
    public String deleteFriend(int firstUserId, int secondUserId) {
        User firstUser = userService.getUser(firstUserId);
        User secondUser = userService.getUser(secondUserId);
        boolean firstCheck = friendshipRepository.existsByFirstUserAndSecondUser(firstUser, secondUser);
        boolean secondCheck = friendshipRepository.existsBySecondUserAndFirstUser(firstUser, secondUser);

        if (firstCheck) {
            friendshipRepository.deleteFriendship(firstUserId, secondUserId);
        } else if (secondCheck) {
            friendshipRepository.deleteFriendship(secondUserId, firstUserId);
        }

        return "Friendship deleted successfully!";
    }

    @Override
    public boolean existsFriendship(int firstUserId, int secondUserId) {
        return friendshipRepository.existsByFirstUserIdAndSecondUserId(firstUserId, secondUserId) ||
                friendshipRepository.existsBySecondUserIdAndFirstUserId(firstUserId, secondUserId);
    }
}