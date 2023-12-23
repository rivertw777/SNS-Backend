package backend.spring.instagram.service;

import backend.spring.instagram.model.dto.request.PostUpdateRequest;
import backend.spring.instagram.model.dto.request.PostUploadRequest;
import backend.spring.instagram.model.entity.Post;
import backend.spring.member.model.entity.Member;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    void registerPost(Member member, PostUploadRequest uploadParam) throws IOException;

    List<Post> getAllPosts();

    void likePost(Member member, Long postId);

    void unlikePost(Member member, Long postId);

    Post getPostById(Long postId);

    void modifyPost(Long postId, PostUpdateRequest updateParam);

    void removePost(Long postId);

}