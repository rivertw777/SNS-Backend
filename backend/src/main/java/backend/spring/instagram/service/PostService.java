package backend.spring.instagram.service;

import backend.spring.instagram.model.dto.PostUploadDto;
import backend.spring.instagram.model.entity.Post;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    void registerPost(PostUploadDto uploadParam) throws IOException;

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long postId);

    //void modifyPost(Long postId, PostUpdateDto updateParam);

    void removePost(Long postId);

    void likePost(Long postId);

    void unlikePost(Long postId);

}