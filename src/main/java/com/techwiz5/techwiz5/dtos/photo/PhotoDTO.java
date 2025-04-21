package com.techwiz5.techwiz5.dtos.photo;

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
public class PhotoDTO {
    private Long id;
    private String fileUrl;
    private Long tripId;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
