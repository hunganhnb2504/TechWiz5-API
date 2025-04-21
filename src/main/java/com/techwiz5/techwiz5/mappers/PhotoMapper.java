package com.techwiz5.techwiz5.mappers;

import com.techwiz5.techwiz5.dtos.photo.PhotoDTO;
import com.techwiz5.techwiz5.entities.Photo;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapper {
    public PhotoDTO toPhotoDTO (Photo model) {
        if (model == null) throw new AppException(ErrorCode.NOTFOUND);
        PhotoDTO photoDTO = PhotoDTO.builder()
                .id(model.getId())
                .tripId(model.getTrip().getId())
                .fileUrl(model.getFileUrl())
                .createdBy(model.getCreatedBy())
                .createdDate(model.getCreatedDate())
                .modifiedBy(model.getModifiedBy())
                .modifiedDate(model.getModifiedDate())
                .build();
        return photoDTO;
    }
}
