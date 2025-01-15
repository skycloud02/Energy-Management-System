package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.entities.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    List<Message> findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<Message> findByReceiverIdAndIsSeenFalse(Long receiverId);
}
