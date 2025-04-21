package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.entities.Photo;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.photo.CreatePhotoRequest;
import com.techwiz5.techwiz5.services.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/photo")
    public ResponseEntity<ResponseObject> create(
            @RequestParam("tripid") long id,
            @RequestParam("image") List<MultipartFile> image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        CreatePhotoRequest createPhotoRequest = new CreatePhotoRequest();
        createPhotoRequest.setFile(image);
        createPhotoRequest.setTripId(id);
        photoService.addPhotos(createPhotoRequest,currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", "")
        );
    }
}
