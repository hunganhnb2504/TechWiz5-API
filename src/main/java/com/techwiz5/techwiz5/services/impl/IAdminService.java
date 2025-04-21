package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.menuadmin.Menu;
import com.techwiz5.techwiz5.dtos.menuadmin.MenuItem;
import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.Role;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.mappers.TripMapper;
import com.techwiz5.techwiz5.mappers.UserMapper;
import com.techwiz5.techwiz5.repositories.ContactRepository;
import com.techwiz5.techwiz5.repositories.TripRepository;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class IAdminService implements AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TripRepository tripRepository;
    private final ContactRepository contactRepository;
    private final TripMapper tripMapper;

    @Override
    public List<Menu> getMenu(User currenUser) {
        List<Menu> menus = new ArrayList<>();
        Set<Role> roles = convertAuthoritiesToRoles(currenUser.getAuthorities());
        if (roles.contains(Role.ADMIN)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "/", "far fa-chart-bar"));
            menuItems2.add(new MenuItem("Course Online", "/course-online", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Course Offline", "/course-offline", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Categories", "/category-list", "fas fa-stream"));
        } else if (roles.contains(Role.USER)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            List<MenuItem> menuItems4 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "/dashboard-teacher", "far fa-chart-bar"));
            menuItems2.add(new MenuItem("Teaching Class", "/teacher/class", "fas fa-door-open"));

        }
        return menus;
    }
    private Set<Role> convertAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }

    @Override
    public List<UserDTO> findAllUsers(User user) {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> findAllTripsCreatedByAdmins(User user) {
        List<User> adminUsers = userRepository.findAllByRole(Role.ADMIN);
        List<Trip> trips = tripRepository.findAllByUserIn(adminUsers);
        return trips.stream()
                .map(tripMapper::toTripDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<ResponseObject> countUsers(User user) {
        long totalUsers = userRepository.count();
        return ResponseEntity.ok(new ResponseObject(true, 200, "ok", totalUsers));
    }

    @Override
    public ResponseEntity<ResponseObject> countTrips(User user) {
        long totalTrips = tripRepository.count();
        return ResponseEntity.ok(new ResponseObject(true, 200, "ok", totalTrips));
    }

    @Override
    public ResponseEntity<ResponseObject> countContacts(User user) {
        long totalContacts = contactRepository.count();
        return ResponseEntity.ok(new ResponseObject(true, 200, "ok", totalContacts));
    }

}