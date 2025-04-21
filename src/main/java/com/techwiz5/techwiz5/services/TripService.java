package com.techwiz5.techwiz5.services;


import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.trip.CreateTrip;
import com.techwiz5.techwiz5.models.trip.EditTrip;

import java.util.List;

public interface TripService {
    List<TripDTO> findAll();
    TripDTO findTripByIdAndUser(Long tripId, User user);
    List<TripDTO> findAllByUser(User user);
    TripDTO create(CreateTrip createTrip, User user);
    TripDTO update(EditTrip editTrip, User user);
    void delete(Long[] ids);

}
