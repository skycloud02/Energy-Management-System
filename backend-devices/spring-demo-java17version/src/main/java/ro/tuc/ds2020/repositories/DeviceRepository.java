package ro.tuc.ds2020.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Device d WHERE d.userId = :userId")
    void deleteByUserId(UUID userId);

    List<Device> findByUserId(UUID userId);
}
