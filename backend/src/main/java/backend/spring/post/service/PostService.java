package backend.spring.post.service;

import backend.spring.post.model.dto.PostUploadDto;
import backend.spring.post.model.entity.Post;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    void registerPost(PostUploadDto uploadParam, String username) throws IOException;

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long postId);

    //void modifyPost(Long postId, PostUpdateDto updateParam);

    void removePost(Long postId);

}