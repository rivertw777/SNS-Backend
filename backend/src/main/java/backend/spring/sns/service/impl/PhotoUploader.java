package backend.spring.sns.service.impl;

import backend.spring.file.FileSystemService;
import backend.spring.file.S3Service;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class PhotoUploader {

    @Autowired
    private final Environment environment;

    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        if ("dev".equals(System.getProperty("spring.profiles.active"))) {
            FileSystemService fileSystemService = new FileSystemService(environment);
            return fileSystemService.uploadPhoto(photo, photoName);
        }
        S3Service s3Service = new S3Service(new AmazonS3Client(), environment);
        return s3Service.uploadPhoto(photo, photoName);
    }

}
