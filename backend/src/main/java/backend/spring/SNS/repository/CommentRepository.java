package backend.spring.SNS.repository;

import backend.spring.SNS.model.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostPostId(Long postId);
}
