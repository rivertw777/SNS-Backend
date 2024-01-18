package backend.spring.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("prod")
@RequiredArgsConstructor
@Service
public class S3Service {

    @Autowired
    private final AmazonS3 amazonS3;

    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String bucket = dotenv.get("cloud.aws.s3.bucket");

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(photo.getSize());
        metadata.setContentType(photo.getContentType());

        amazonS3.putObject(bucket, photoName, photo.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, photoName).toString();
    }

}
