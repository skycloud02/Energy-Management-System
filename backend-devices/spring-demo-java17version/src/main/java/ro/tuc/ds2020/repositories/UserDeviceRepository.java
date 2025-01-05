package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.UserDevice;

import java.util.List;
import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UserDevice ud WHERE ud.userId = :userId")
    void deleteByUserId(UUID userId);

    @Query("SELECT DISTINCT ud.userId FROM UserDevice ud")
    List<UUID> findDistinctUserIds();
}
