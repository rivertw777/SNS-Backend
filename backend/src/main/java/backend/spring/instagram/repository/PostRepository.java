package backend.spring.instagram.repository;

import backend.spring.instagram.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}