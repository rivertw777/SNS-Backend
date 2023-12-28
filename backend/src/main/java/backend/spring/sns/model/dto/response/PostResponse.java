package backend.spring.sns.model.dto.response;

import backend.spring.member.model.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "게시물 조회 응답 DTO")
public record PostResponse(Long postId, Member author, String photoUrl, String caption, String location, boolean isLike ) {
    public PostResponse{

    }
}
