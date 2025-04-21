package com.techwiz5.techwiz5.models.trip;


import com.techwiz5.techwiz5.entities.Category;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class CreateTrip {
    private Timestamp endDate;
    private Timestamp startDate;
    private String tripName;
    private String destination;
    private BigDecimal budget;
    private Integer groupSize;
    private List<Long> categoriesId;
}
