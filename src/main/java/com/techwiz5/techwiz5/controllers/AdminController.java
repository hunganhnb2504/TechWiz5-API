package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;

import com.techwiz5.techwiz5.dtos.menuadmin.Menu;
import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.techwiz5.techwiz5.dtos.UserDTO;
import java.util.List;


@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("count/user")
    public ResponseEntity<ResponseObject> getTotalUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        return adminService.countUsers(currenUser);
    }

    @GetMapping("count/trip")
    public ResponseEntity<ResponseObject> getTotalTrips() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        return adminService.countTrips(currenUser);
    }

    @GetMapping("count/contact")
    public ResponseEntity<ResponseObject> getTotalContacts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        return adminService.countContacts(currenUser);
    }

    @GetMapping("menu")
    public ResponseEntity<ResponseObject> getMenu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<Menu> menuItems = adminService.getMenu(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", menuItems)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllTripsCreatedByAdmins(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<TripDTO> trips = adminService.findAllTripsCreatedByAdmins(currenUser);
        if (!user.getRole().equals("ADMIN")) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", trips)
        );
    }
    @GetMapping("user")
    ResponseEntity<ResponseObject> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currenUser = (User) auth.getPrincipal();
        List<UserDTO> list = adminService.findAllUsers(currenUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }


}
