package com.techwiz5.techwiz5.services;

import com.techwiz5.techwiz5.entities.Notification;
import com.techwiz5.techwiz5.entities.User;

import java.util.List;

public interface NotificationService {
    void sendDailyExpenseReminder(User user);
    void sendUpcomingTripReminder(User user);
    void sendBudgetAlert(User user, String tripName);
    List<Notification> getUnreadNotifications(User user);
    List<Notification> getAllNotifications(User user);
    void markNotificationAsRead(Long notificationId, User user);
}
