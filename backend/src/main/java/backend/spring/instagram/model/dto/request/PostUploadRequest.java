package backend.spring.instagram.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record PostUploadRequest(MultipartFile[] photos, String caption, String location) {
    public PostUploadRequest {
    }

}
