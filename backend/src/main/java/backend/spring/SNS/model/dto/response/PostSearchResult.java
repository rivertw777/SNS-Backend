package backend.spring.SNS.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public record PostSearchResult(Long postId, String location, String caption, LocalDateTime createdAt, Long memberId,
                               String memberName, String memberAvatarUrl) {
    @QueryProjection
    public PostSearchResult(Long postId, String location, String caption, LocalDateTime createdAt, Long memberId,
                            String memberName, String memberAvatarUrl) {
        this.postId = postId;
        this.location = location;
        this.caption = caption;
        this.createdAt = createdAt;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAvatarUrl = memberAvatarUrl;
    }
}
