package com.techwiz5.techwiz5.dtos.category;

import com.techwiz5.techwiz5.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
    private UserDTO user;
}