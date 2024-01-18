package backend.spring.file;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("local")
@RequiredArgsConstructor
@Service
public class FileSystemService {

    public String uploadPhoto(MultipartFile photo, String photoName) throws IOException {
        Dotenv dotenv = Dotenv.load();
        String uploadPath = dotenv.get("photo.save.dir");
        String accessUrl = dotenv.get("photo.access.url");

        // 사진 파일 저장
        String projectPath = System.getProperty("user.dir") + uploadPath;
        File saveFile = new File(projectPath, photoName);
        photo.transferTo(saveFile);
        return accessUrl + photoName;
    }

}
