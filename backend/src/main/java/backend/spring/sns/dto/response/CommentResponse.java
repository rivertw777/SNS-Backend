package backend.spring.sns.dto.response;

import backend.spring.member.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "게시물 댓글 조회 응답 DTO")
public record CommentResponse(Long CommentId, Member author, String message, LocalDateTime createdAt) {
    public CommentResponse{}
}
