package backend.spring.sns.model.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record PostUploadRequest(MultipartFile photo, String caption, String location) {
    public PostUploadRequest {
    }
}
