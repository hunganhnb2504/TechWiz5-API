package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;

import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.trip.CreateTrip;
import com.techwiz5.techwiz5.models.trip.EditTrip;
import com.techwiz5.techwiz5.services.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping("/trip")
    ResponseEntity<ResponseObject> getAllByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<TripDTO> list = tripService.findAllByUser(currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @GetMapping("/trip/{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable("id") long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        TripDTO list = tripService.findTripByIdAndUser(id, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", list)
        );
    }

    @PostMapping("/trip")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateTrip createTrip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        TripDTO tripDTO = tripService.create(createTrip, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", tripDTO)
        );
    }
    @PutMapping("/trip")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody EditTrip editTrip) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        TripDTO tripDTO = tripService.update(editTrip, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", tripDTO)
        );
    }

    @DeleteMapping("/trip")
    ResponseEntity<ResponseObject> update(@RequestBody Long[] ids) {
        tripService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }
}
