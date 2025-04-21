package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import com.techwiz5.techwiz5.entities.Category;
import com.techwiz5.techwiz5.entities.Role;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.mappers.TripMapper;
import com.techwiz5.techwiz5.models.trip.CreateTrip;
import com.techwiz5.techwiz5.models.trip.EditTrip;
import com.techwiz5.techwiz5.repositories.CategoryRepository;
import com.techwiz5.techwiz5.repositories.TripRepository;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ITripService implements TripService {
    private final CategoryRepository categoryRepository;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final UserRepository userRepository;

    @Override
    public List<TripDTO> findAll() {
        return tripRepository.findByIsFeaturedTrue().stream().map(tripMapper::toTripDTO).collect(Collectors.toList());
    }

    @Override
    public List<TripDTO> findAllByUser(User user) {
        List<Trip> trips = tripRepository.findAllByUser(user);
        return trips.stream()
                .map(tripMapper::toTripDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TripDTO findTripByIdAndUser(Long tripId, User user) {
        Trip trip = tripRepository.findByIdAndUser(tripId, user)
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOTFOUND));
        return tripMapper.toTripDTO(trip);
    }

    @Override
    public TripDTO create(CreateTrip createTrip, User user) {
        List<Category> categories = categoryRepository.findAllById(createTrip.getCategoriesId())
                .stream()
                .toList();
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOTFOUND);
        }
        Trip trip = Trip.builder()
                .user(user)
                .categories(categories)
                .startDate(createTrip.getStartDate())
                .endDate(createTrip.getEndDate())
                .budget(createTrip.getBudget())
                .groupSize(createTrip.getGroupSize())
                .tripDestination(createTrip.getDestination())
                .tripName(createTrip.getTripName())
                .expenses(new ArrayList<>())
                .photos(new ArrayList<>())
                .isFeatured(false)
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();
        tripRepository.save(trip);
        return tripMapper.toTripDTO(trip);
    }

    @Override
    public TripDTO update(EditTrip editTrip, User user) {
        Trip tripExisting = tripRepository.findById(editTrip.getId())
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOTFOUND));
        List<Category> categories = categoryRepository.findAllById(editTrip.getCategoriesId())
                .stream()
                .toList();
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOTFOUND);
        }
        tripExisting.setBudget(editTrip.getBudget());
        tripExisting.setTripDestination(editTrip.getDestination());
        tripExisting.setTripName(editTrip.getTripName());
        tripExisting.setEndDate(editTrip.getEndDate());
        tripExisting.setStartDate(editTrip.getStartDate());
        tripExisting.setCreatedBy(user.getFullName());
        tripExisting.setModifiedBy(user.getFullName());
        tripExisting.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        tripExisting.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        tripRepository.save(tripExisting);
        return tripMapper.toTripDTO(tripExisting);
    }


    @Override
    public void delete(Long[] ids) {
        tripRepository.deleteAllById(List.of(ids));
    }

}