package backend.spring.sns.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시물 수정 요청 DTO")
public record PostUpdateRequest(String photoUrl, String caption, String location) {
    public PostUpdateRequest{
    }
}
