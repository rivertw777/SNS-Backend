package backend.spring.instagram.model.dto.mapper;

import backend.spring.instagram.model.dto.response.PostResponse;
import backend.spring.instagram.model.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {
    public PostResponse toPostResponse(Post post, Long userId) {
        return new PostResponse(
                post.getPostId(),
                post.getAuthor(),
                post.getPhotoUrl(),
                post.getCaption(),
                post.getLocation(),
                post.isLikeUser(userId)
        );
    }
}
