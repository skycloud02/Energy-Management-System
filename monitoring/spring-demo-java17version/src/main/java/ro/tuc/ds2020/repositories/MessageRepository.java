package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.tuc.ds2020.entities.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE messages", nativeQuery = true)
    void truncateTable();
}
