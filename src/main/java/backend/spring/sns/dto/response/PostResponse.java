package backend.spring.sns.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시물 조회 응답 DTO")
public record PostResponse(Long postId, String authorName, String avatarUrl, String photoUrl, String caption, String location, boolean isLike ) {

    public PostResponse{
    }

}
