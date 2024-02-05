package backend.spring.sns.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시물 댓글 조회 응답 DTO")
public record CommentResponse(Long CommentId, String authorName, String avatarUrl, String message, LocalDateTime createdAt) {

    public CommentResponse{
    }

}
