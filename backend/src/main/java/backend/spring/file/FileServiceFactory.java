package backend.spring.file;

import com.amazonaws.services.s3.AmazonS3Client;

public class FileServiceFactory {

    public static FileService create(String profile){
        if (profile.equals("local")){
            return new FileSystemService();
        }
        return new S3Service(new AmazonS3Client());
    }

}
