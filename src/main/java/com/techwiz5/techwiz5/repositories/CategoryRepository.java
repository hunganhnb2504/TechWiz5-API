package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Category;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser(User user);
    Optional<Category> findByName(String name);
}
