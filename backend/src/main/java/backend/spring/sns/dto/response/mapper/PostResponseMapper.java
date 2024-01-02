package backend.spring.sns.dto.response.mapper;

import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.model.entity.Post;
import backend.spring.sns.service.SNSService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {
    public List<PostResponse> toPostResponses( List<Post> posts,  Long memberId, SNSService snsService) {
        return posts.stream()
                .map(post -> toPostResponse(post, memberId, snsService))
                .collect(Collectors.toList());
    }

    public PostResponse toPostResponse(Post post, Long userId, SNSService snsService) {
        return new PostResponse(
                post.getPostId(),
                post.getAuthor(),
                post.getPhotoUrl(),
                post.getCaption(),
                post.getLocation(),
                snsService.isPostLikedByUser(post.getPostId(), userId)
        );
    }
}
