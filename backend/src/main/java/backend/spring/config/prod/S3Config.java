package backend.spring.config.prod;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
public class S3Config {

    @Bean
    public AmazonS3Client amazonS3Client() {
        Dotenv dotenv = Dotenv.load();
        String accessKey = dotenv.get("cloud.aws.credentials.access-key");
        String secretKey = dotenv.get("cloud.aws.credentials.secret-key");
        String region = dotenv.get("cloud.aws.region.static");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

}