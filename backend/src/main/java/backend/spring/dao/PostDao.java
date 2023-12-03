package backend.spring.dao;

import backend.spring.dao.dto.PostUpdateDto;
import backend.spring.model.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao {

    void save(Post post);

    List<Post> findAll();

    Optional<Post> findById(Long postId);

    void update(Long postId, PostUpdateDto updateParam);

    void delete(Long postId);

}