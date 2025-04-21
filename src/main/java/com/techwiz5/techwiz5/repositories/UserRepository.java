package com.techwiz5.techwiz5.repositories;


import com.techwiz5.techwiz5.entities.Role;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    long count();
    Optional<User> findByEmail(String email);
    User findByRole(Role role);
    List<User> findAllByRole(Role role);
}