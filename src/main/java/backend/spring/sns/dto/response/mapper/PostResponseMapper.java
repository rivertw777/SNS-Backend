package backend.spring.sns.dto.response.mapper;

import backend.spring.member.model.entity.Member;
import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.model.entity.Post;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {

    public List<PostResponse> toPostResponses( List<Post> posts,  Member currentMember) {
        return posts.stream()
                .map(post -> toPostResponse(post, currentMember))
                .collect(Collectors.toList());
    }

    public PostResponse toPostResponse(Post post, Member currentMember) {
        return new PostResponse(
                post.getPostId(),
                post.getAuthor().getName(),
                post.getAuthor().getAvatarUrl(),
                post.getPhotoUrl(),
                post.getCaption(),
                post.getLocation(),
                isLikeMember(post, currentMember)
        );
    }

    private boolean isLikeMember(Post post, Member currentMember) {
        Set<Member> likeMemberSet = post.getLikeMemberSet();
        return likeMemberSet.stream()
                .anyMatch(member -> member.getMemberId().equals(currentMember.getMemberId()));
    }

}
