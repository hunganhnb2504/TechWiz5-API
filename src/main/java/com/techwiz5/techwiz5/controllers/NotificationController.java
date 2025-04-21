package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.entities.Notification;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.services.NotificationService;
import com.techwiz5.techwiz5.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        User currentUser = (User) auth.getPrincipal();
        return notificationService.getUnreadNotifications(currentUser);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        User currentUser = (User) auth.getPrincipal();
        return notificationService.getAllNotifications(currentUser);
    }

    @PostMapping("/mark-as-read/{id}")
    public void markNotificationAsRead(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        User currentUser = (User) auth.getPrincipal();
        notificationService.markNotificationAsRead(id,currentUser);
    }
}
