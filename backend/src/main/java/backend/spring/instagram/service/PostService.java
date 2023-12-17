package backend.spring.instagram.service;

import backend.spring.instagram.model.dto.request.PostUploadRequest;
import backend.spring.instagram.model.entity.Post;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    void registerPost(String username, PostUploadRequest uploadParam) throws IOException;

    List<Post> getAllPosts();

    void likePost(String username, Long postId);

    void unlikePost(String username, Long postId);

    Optional<Post> getPostById(Long postId);

    //void modifyPost(Long postId, PostUpdateDto updateParam);

    void removePost(Long postId);

}