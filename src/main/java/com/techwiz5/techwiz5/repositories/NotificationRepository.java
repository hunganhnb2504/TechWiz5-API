package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Notification;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserAndIsReadFalse(User user);
    List<Notification> findAllByUser(User user);
}
