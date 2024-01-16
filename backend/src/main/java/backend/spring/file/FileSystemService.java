package backend.spring.file;

import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;

@Profile("local")
@RequiredArgsConstructor
@Service
public class FileSystemService {

    @Autowired
    private final Environment environment;

    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        String uploadPath = environment.getProperty("photo.save.dir");
        String accessUrl = environment.getProperty("photo.access.url");

        // 사진 파일 저장
        String projectPath = System.getProperty("user.dir") + uploadPath;
        File saveFile = new File(projectPath, photoName);
        photo.transferTo(saveFile);
        return accessUrl + photoName;
    }
}
