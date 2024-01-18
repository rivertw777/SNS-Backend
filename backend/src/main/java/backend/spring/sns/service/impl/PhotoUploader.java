package backend.spring.sns.service.impl;

import backend.spring.file.FileSystemService;
import backend.spring.file.S3Service;
import com.amazonaws.services.s3.AmazonS3Client;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PhotoUploader {

    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        if ("local".equals(System.getProperty("spring.profiles.active"))) {
            FileSystemService fileSystemService = new FileSystemService();
            return fileSystemService.uploadPhoto(photo, photoName);
        }
        S3Service s3Service = new S3Service(new AmazonS3Client());
        return s3Service.uploadPhoto(photo, photoName);
    }
}
