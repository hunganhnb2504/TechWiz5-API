package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    long count();
}
