package com.techwiz5.techwiz5.models.photo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class CreatePhotoRequest {
    private Long tripId;
    private List<MultipartFile> file;
}
