package backend.spring.model.post.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostUploadDto(MultipartFile[] photos, String caption, String location) {
    public PostUploadDto {
    }

}
