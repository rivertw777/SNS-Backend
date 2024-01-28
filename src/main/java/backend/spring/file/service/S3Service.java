package backend.spring.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Profile("prod")
@Service
public class S3Service implements FileService {

    @Autowired
    private final AmazonS3 amazonS3;

    @Autowired
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(photo.getSize());
        metadata.setContentType(photo.getContentType());

        amazonS3.putObject(bucket, photoName, photo.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, photoName).toString();
    }

}
