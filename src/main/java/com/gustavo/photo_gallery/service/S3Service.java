package com.gustavo.photo_gallery.service;

import com.gustavo.photo_gallery.dto.PhotoFile;
import com.gustavo.photo_gallery.dto.PhotoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.List;

@Service
public class S3Service {
    private final S3Client s3Client;
    private final String bucketName;

    public S3Service(S3Client s3Client, @Value("${app.aws.s3.bucket-name}") String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(file.getBytes())
        );
    }

    public List<PhotoResponse> listFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(object -> {

                    HeadObjectResponse metadata = s3Client.headObject(
                            HeadObjectRequest.builder()
                                    .bucket(bucketName)
                                    .key(object.key())
                                    .build()
                    );

                    return new PhotoResponse(
                            object.key(),
                            metadata.contentType(),
                            object.size(),
                            "/photos/" + object.key()
                    );
                })
                .toList();
    }

    public PhotoFile getFile(String key) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);

        return new PhotoFile(
                response.asByteArray(),
                response.response().contentType()
        );
    }

    public void deleteFile(String key) {

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }
}
