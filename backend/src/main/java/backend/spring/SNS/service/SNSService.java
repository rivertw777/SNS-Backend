package backend.spring.SNS.service;

import backend.spring.SNS.model.dto.request.CommentWriteRequest;
import backend.spring.SNS.model.dto.request.PostUpdateRequest;
import backend.spring.SNS.model.dto.request.PostUploadRequest;
import backend.spring.SNS.model.entity.Comment;
import backend.spring.SNS.model.entity.Post;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SNSService {

    void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException;

    List<Post> getAllPosts();

    void writeComment(Long memberId, Long postId, CommentWriteRequest wirteParam);

    List<Comment> getComments(Long postId);

    void likePost(Long memberId, Long postId);

    void unlikePost(Long memberId, Long postId);

    Post getPostById(Long postId);

    void modifyPost(Long postId, PostUpdateRequest updateParam);

    void removePost(Long postId);

}