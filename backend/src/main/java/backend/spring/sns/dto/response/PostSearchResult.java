package backend.spring.sns.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "검색 결과 응답 DTO")
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
