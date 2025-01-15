package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.entities.Message;
import ro.tuc.ds2020.entities.TypingNotification;
import ro.tuc.ds2020.services.MessageService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/typing")
    @SendTo("/topic/messages")
    public void typingNotification(@Payload TypingNotification typingNotification) {
        simpMessagingTemplate.convertAndSend(
                "/topic/typing/" + typingNotification.getReceiverId(),
                typingNotification
        );
    }


    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message sendMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.now());
        messageService.saveMessage(
                message.getSenderId(),
                message.getSenderRole(),
                message.getReceiverId(),
                message.getReceiverRole(),
                message.getContent()
        );

        // Broadcast notification to the receiver
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications/" + message.getReceiverId(),
                "New message from " + message.getSenderId()
        );

        // Return the message for chat display
        return message;
    }

    @MessageMapping("/sendBulkMessage")
    public void sendBulkMessage(@Payload List<Message> messages) {
        for (Message message : messages) {
            message.setTimestamp(LocalDateTime.now());
            messageService.saveMessage(
                    message.getSenderId(),
                    message.getSenderRole(),
                    message.getReceiverId(),
                    message.getReceiverRole(),
                    message.getContent()
            );

            // Send notification to each user
            simpMessagingTemplate.convertAndSend(
                    "/topic/notifications/" + message.getReceiverId(),
                    "New message from admin"
            );

            // Broadcast the message to each user
            simpMessagingTemplate.convertAndSend(
                    "/topic/messages/" + message.getReceiverId(),
                    message
            );
        }
    }


    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam String userId1, @RequestParam String userId2) {
        return messageService.getMessagesBetweenUsers(userId1, userId2);
    }

    @GetMapping("/unread")
    public List<Message> getUnreadMessages(@RequestParam Long receiverId) {
        return messageService.getUnreadMessagesForUser(receiverId);
    }
}
