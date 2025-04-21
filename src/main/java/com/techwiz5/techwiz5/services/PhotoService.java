package com.techwiz5.techwiz5.services;

import com.techwiz5.techwiz5.dtos.photo.PhotoDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.photo.CreatePhotoRequest;


import java.util.List;

public interface PhotoService {
    List<PhotoDTO> addPhotos(CreatePhotoRequest request, User user);

}
