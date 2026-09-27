package com.gustavo.photo_gallery.controller;

import com.gustavo.photo_gallery.dto.PhotoFile;
import com.gustavo.photo_gallery.dto.PhotoResponse;
import com.gustavo.photo_gallery.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/photos")
public class PhotoController {

    private final S3Service s3Service;

    public PhotoController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        s3Service.uploadFile(file);

        return ResponseEntity.ok(
                "Upload realizado: " + file.getOriginalFilename()
        );
    }

    @GetMapping
    public ResponseEntity<List<PhotoResponse>> listPhotos() {

        List<PhotoResponse> photos = s3Service.listFiles();

        return ResponseEntity.ok(photos);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String key) {

        s3Service.deleteFile(key);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{key}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String key) {

        PhotoFile photo = s3Service.getFile(key);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(photo.contentType()))
                .body(photo.data());
    }
}
