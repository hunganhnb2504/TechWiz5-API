package com.techwiz5.techwiz5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Table(name = "category")
public class Category extends BaseEntity{
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    @ManyToMany(mappedBy = "categories")
    private List<Trip> trips;

}
