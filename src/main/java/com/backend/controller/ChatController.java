package com.backend.controller;

import com.backend.model.Chat;
import com.backend.repository.TeamRepository;
import com.backend.repository.UserRepository;
import com.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {

    @Autowired
    ChatService chatService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/get-chat")
    public List<Chat> getChat(@RequestParam Integer id) {
        return chatService.getAllMessagesById(id);
    }

    //    @PostMapping("/save-chat")
//    public String saveChat(@RequestParam String message, @RequestParam Integer sender, @RequestParam Integer teamId){
//        Chat newChat = Chat.builder().message(message).sender(userRepository.findByUserId(sender)).time(LocalTime.now()).teamId(teamRepository.findByTeamId(teamId)).build();
//        chatService.saveChat(newChat);
//        return "Chat saved successfully!";
//    }
    @PostMapping("/save-chat")
    public String saveChat(@RequestBody Chat chat) {
        Chat newChat = Chat.builder().time(LocalTime.now()).message(chat.getMessage()).sender(chat.getSender()).teamId(chat.getTeamId()).build();
        chatService.saveChat(newChat);
        return "Chat saved successfully!";
    }
}
