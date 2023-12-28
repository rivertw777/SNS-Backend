package backend.spring.sns.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "게시물 업로드 요청 DTO")
public record PostUploadRequest(MultipartFile photo, String caption, String location) {
    public PostUploadRequest {
    }
}
