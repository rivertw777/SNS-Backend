package backend.spring.SNS.repository;

import backend.spring.SNS.model.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Optional<Post> findByPostId(Long postId);
}