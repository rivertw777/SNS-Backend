package backend.spring.instagram.service;

import backend.spring.instagram.model.dto.CommentWriteDto;
import backend.spring.instagram.model.entity.Comment;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    Comment writeComment(String username, Long PostId, CommentWriteDto wirteParam);
    List<Comment> getComments(Long postId);

}
