package backend.spring.sns.service;

import backend.spring.sns.dto.request.CommentWriteRequest;
import backend.spring.sns.dto.request.PostSearchCondition;
import backend.spring.sns.dto.request.PostUploadRequest;
import backend.spring.sns.dto.response.CommentResponse;
import backend.spring.sns.dto.response.PostResponse;
import backend.spring.sns.dto.response.PostSearchResult;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SnsService {

    void registerPost(Long memberId, PostUploadRequest uploadParam) throws IOException;

    List<PostResponse> getAllPosts(Long memberId);

    void writeComment(Long memberId, Long postId, CommentWriteRequest wirteParam);

    List<CommentResponse> getComments(Long postId);

    void likePost(Long memberId, Long postId);

    void unlikePost(Long memberId, Long postId);

    List<PostSearchResult> searchByConditions(PostSearchCondition conditionParam);


}