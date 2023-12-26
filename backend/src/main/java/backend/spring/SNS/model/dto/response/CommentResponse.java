package backend.spring.SNS.model.dto.response;

import backend.spring.member.model.entity.Member;
import java.time.LocalDateTime;

public record CommentResponse(Long CommentId, Member author, String message, LocalDateTime createdAt) {
    public CommentResponse{}
}
