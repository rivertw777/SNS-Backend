package backend.spring.file.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadPhoto(MultipartFile photo, String photoName) throws IOException;
}
