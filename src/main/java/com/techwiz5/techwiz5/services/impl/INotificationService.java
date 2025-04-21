package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.entities.Notification;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.repositories.NotificationRepository;
import com.techwiz5.techwiz5.repositories.TripRepository;
import com.techwiz5.techwiz5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class INotificationService implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TripRepository tripRepository;

    @Override
    public void sendDailyExpenseReminder(User user) {
        String message = "Nhắc nhở: Đừng quên ghi lại chi phí hàng ngày của bạn.";
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setTimestamp(new Timestamp(System.currentTimeMillis()));
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public void sendUpcomingTripReminder(User user) {
        List<Trip> trips = tripRepository.findAllByUser(user);
        long currentTime = System.currentTimeMillis();
        long oneDayInMillis = 24 * 60 * 60 * 1000; // 1 ngày

        for (Trip trip : trips) {
            long startTime = trip.getStartDate().getTime();
            if (startTime - currentTime <= oneDayInMillis && startTime - currentTime > 0) {
                String message = "Nhắc nhở: Bạn có chuyến đi sắp tới!";
                Notification notification = new Notification();
                notification.setUser(user);
                notification.setMessage(message);
                notification.setTimestamp(new Timestamp(currentTime));
                notification.setIsRead(false);
                notificationRepository.save(notification);
            }
        }
    }

    @Override
    public void sendBudgetAlert(User user, String tripName) {
        String message = "Cảnh báo: Bạn sắp vượt quá ngân sách cho chuyến đi " + tripName;
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setTimestamp(new Timestamp(System.currentTimeMillis()));
        notification.setIsRead(false);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findAllByUserAndIsReadFalse(user);
    }

    @Override
    public List<Notification> getAllNotifications(User user) {
        return notificationRepository.findAllByUser(user);
    }

    @Override
    public void markNotificationAsRead(Long notificationId, User user) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
