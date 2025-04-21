package com.techwiz5.techwiz5.models.photo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdatePhotoRequest {
    private Long photoId;
    private List<MultipartFile> photo;
}
