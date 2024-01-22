package backend.spring.file.service;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("local")
@Service
public class FileSystemService implements FileService {

    @Autowired
    @Value("${photo.save.dir}")
    private String uploadPath;

    @Autowired
    @Value("${photo.access.url}")
    private String accessUrl;

    @Override
    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        // 사진 파일 저장
        String projectPath = System.getProperty("user.dir") + uploadPath;
        File saveFile = new File(projectPath, photoName);
        photo.transferTo(saveFile);
        return accessUrl + photoName;
    }

}
