package backend.spring.sns.service;

import backend.spring.sns.model.dto.request.CommentWriteRequest;
import backend.spring.sns.model.dto.request.PostSearchCondition;
import backend.spring.sns.model.dto.request.PostUpdateRequest;
import backend.spring.sns.model.dto.request.PostUploadRequest;
import backend.spring.sns.model.dto.response.PostSearchResult;
import backend.spring.sns.model.entity.Comment;
import backend.spring.sns.model.entity.Post;
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

    List<PostSearchResult> searchByConditions(PostSearchCondition conditionParam);

    Post getPostById(Long postId);

    void modifyPost(Long postId, PostUpdateRequest updateParam);

    void removePost(Long postId);

}