package backend.spring.sns.model.dto.response.mapper;

import backend.spring.sns.model.dto.response.CommentResponse;
import backend.spring.sns.model.entity.Comment;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CommentResponseMapper {
    public List<CommentResponse> toCommentResponses(List<Comment> comments) {
        return comments.stream()
                .map(comment -> toPostResponse(comment))
                .collect(Collectors.toList());
    }

    public CommentResponse toPostResponse(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getAuthor(),
                comment.getMessage(),
                comment.getCreatedAt()
        );
    }
}