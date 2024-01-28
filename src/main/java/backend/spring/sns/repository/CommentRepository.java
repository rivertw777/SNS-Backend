package backend.spring.sns.repository;

import backend.spring.sns.model.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostPostId(Long postId);

}
