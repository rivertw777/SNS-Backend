package backend.spring.sns.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 작성 요청 DTO")
public record CommentWriteRequest(String message) {

    public CommentWriteRequest {
    }

}
