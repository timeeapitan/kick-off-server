package com.backend.service;

import com.backend.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> getAllMessagesById(Integer id);

    void saveChat(Chat chat);

    String deleteChat(Integer teamId);
}
