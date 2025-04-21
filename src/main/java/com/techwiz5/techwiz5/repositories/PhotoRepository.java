package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Photo;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByUserAndTrip(User user, Trip trip);
}
