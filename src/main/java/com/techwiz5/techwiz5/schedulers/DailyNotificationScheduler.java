package com.techwiz5.techwiz5.schedulers;

import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyNotificationScheduler {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyReminders() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            notificationService.sendDailyExpenseReminder(user);
        }
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendTripReminders() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            notificationService.sendUpcomingTripReminder(user);
        }
    }

}
