package backend.spring.instagram.model.dto.response.mapper;

import backend.spring.instagram.model.dto.response.PostResponse;
import backend.spring.instagram.model.entity.Post;
import backend.spring.member.model.entity.Member;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {
    public List<PostResponse> toPostResponses(List<Post> posts, Long userId) {
        return posts.stream()
                .map(post -> toPostResponse(post, userId))
                .collect(Collectors.toList());
    }

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
