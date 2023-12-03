package backend.spring.service;

import backend.spring.dao.dto.PostUpdateDto;
import backend.spring.model.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    void registerPost(Post post);

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long postId);

    void modifyPost(Long postId, PostUpdateDto updateParam);

    void removePost(Long postId);

}