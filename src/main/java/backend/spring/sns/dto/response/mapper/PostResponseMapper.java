package backend.spring.sns.dto.response.mapper;

import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.model.entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {

    public List<PostResponse> toPostResponses( List<Post> posts,  Long memberId) {
        return posts.stream()
                .map(post -> toPostResponse(post, memberId))
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