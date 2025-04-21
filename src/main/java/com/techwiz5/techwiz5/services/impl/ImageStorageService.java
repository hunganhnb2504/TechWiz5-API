package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.services.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageService implements StorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new AppException(ErrorCode.INITIALIZE);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList("png", "jpg", "jpeg", "bmp").contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file");
            }
            // check if file is an image
            if (!isImageFile(file)) {
                throw new RuntimeException("You can upload image file only");
            }
            // check file size <= 5MB
            float fileSizeInMegabytes = file.getSize() / 1_000_000f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5MB");
            }
            // rename file
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName))
                    .normalize().toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside the current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException exception) {
            throw new RuntimeException("Failed to store file.", exception);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load stored files.", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Failed to read file: " + fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {
        try {
            Files.walk(this.storageFolder)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to delete file: " + file, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete files", e);
        }
    }
}
