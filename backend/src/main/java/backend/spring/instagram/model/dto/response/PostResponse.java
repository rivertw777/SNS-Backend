package backend.spring.instagram.model.dto.response;

import backend.spring.member.model.entity.Member;

public record PostResponse(Long postId, Member author, String photoUrl, String caption, String location, boolean isLike ) {
    public PostResponse{

    }
}
