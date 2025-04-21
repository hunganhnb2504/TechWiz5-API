package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByUser(User user);
    Optional<Trip> findByIdAndUser(Long id, User user);
    long count();
    List<Trip> findByIsFeaturedTrue();
    List<Trip> findAllByUserIn(List<User> users);
}
