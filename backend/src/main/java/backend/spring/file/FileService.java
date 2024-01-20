package backend.spring.file;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadPhoto(MultipartFile photo, String photoName) throws IOException;
}
