package com.backend.service;

import com.backend.model.User;

import java.util.Date;
import java.util.Set;

public interface FriendshipService {
    boolean saveFriendship(int firstUserId, int secondUserId);

    Set<User> getFriends(int UserId);

    Date getDate(int firstUserId, int secondUserId);

    String deleteFriend(int firstUserId, int secondUserId);

    boolean existsFriendship(int firstUserId, int secondUserId);

//    String deleteFriend(Friendship friendship);

//    Friendship getFriendshipByFirstUserIdAndSecondUserId(int firstUserId, int secondUserId);
}
