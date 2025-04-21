package com.techwiz5.techwiz5.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Table(name = "trip")
public class Trip extends  BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "trip_category",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
    @OneToMany(mappedBy = "trip")
    private List<Expense> expenses;

    @Column(name = "trip_name", nullable = false)
    private String tripName;

    @Column(name = "destination", nullable = false)
    private String tripDestination;

    @Column(name = "budget", nullable = false)
    private BigDecimal budget;

    @Column(name = "group_size", nullable = false)
    private Integer groupSize;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @OneToMany(mappedBy = "trip")
    private List<Photo> photos;


}
