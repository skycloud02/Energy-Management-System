package ro.tuc.ds2020.services;

import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Message;
import ro.tuc.ds2020.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(String senderId, String senderRole, String receiverId, String receiverRole, String content) {
        Message message = new Message();
        message.setSenderId(senderId);
        message.setSenderRole(senderRole);
        message.setReceiverId(receiverId);
        message.setReceiverRole(receiverRole);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setIsSeen(false);
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(String userId1, String userId2) {
        return messageRepository.findBySenderIdAndReceiverId(userId1, userId2);
    }

    public List<Message> getUnreadMessagesForUser(Long receiverId) {
        return messageRepository.findByReceiverIdAndIsSeenFalse(receiverId);
    }
}