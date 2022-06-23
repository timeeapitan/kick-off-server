package com.backend.service.impl;

import com.backend.model.Chat;
import com.backend.repository.ChatRepository;
import com.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> getAllMessagesById(Integer id) {
        return chatRepository.getAllMessagesById(id);
    }

    @Override
    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public String deleteChat(Integer teamId) {
        chatRepository.deleteChatFromTeam(teamId);
        return "Chat deleted successfully!";
    }
}
