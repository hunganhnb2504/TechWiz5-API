package com.techwiz5.techwiz5.services;



import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.menuadmin.Menu;
import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface AdminService {
    List<Menu> getMenu(User currenUser);
    List<UserDTO> findAllUsers(User user);
    ResponseEntity<ResponseObject> countUsers(User user);
    ResponseEntity<ResponseObject> countTrips(User user);
    ResponseEntity<ResponseObject> countContacts(User user);
    List<TripDTO> findAllTripsCreatedByAdmins(User user);
}