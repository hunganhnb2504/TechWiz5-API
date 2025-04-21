package com.techwiz5.techwiz5.services.impl;


import com.techwiz5.techwiz5.dtos.photo.PhotoDTO;
import com.techwiz5.techwiz5.entities.Photo;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.mappers.PhotoMapper;

import com.techwiz5.techwiz5.models.photo.CreatePhotoRequest;
import com.techwiz5.techwiz5.models.photo.UpdatePhotoRequest;
import com.techwiz5.techwiz5.repositories.PhotoRepository;
import com.techwiz5.techwiz5.repositories.TripRepository;
import com.techwiz5.techwiz5.repositories.UserRepository;
import com.techwiz5.techwiz5.services.PhotoService;
import com.techwiz5.techwiz5.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IPhotoService implements PhotoService {

    private final PhotoRepository photoRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final ImageStorageService imageStorageService;
    private final PhotoMapper photoMapper;

    @Override
    public List<PhotoDTO> addPhotos(CreatePhotoRequest request,User user) throws AppException {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOTFOUND));
        List<Photo> photos = request.getFile().stream()
                .map(fileUrl -> {
                    String storedFileUrl = storageService.storeFile(fileUrl);
                    return Photo.builder()
                            .trip(trip)
                            .user(user)
                            .fileUrl("http://localhost:8080/api/v1/FileUpload/files/" + storedFileUrl)
                            .build();
                })
                .collect(Collectors.toList());
        photos = photoRepository.saveAll(photos);
        return photos.stream()
                .map(photoMapper::toPhotoDTO)
                .collect(Collectors.toList());
    }

}
