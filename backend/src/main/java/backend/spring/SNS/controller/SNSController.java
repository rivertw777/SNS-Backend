package backend.spring.SNS.controller;

import backend.spring.SNS.model.dto.response.CommentResponse;
import backend.spring.SNS.model.dto.response.mapper.CommentResponseMapper;
import backend.spring.SNS.model.dto.response.mapper.PostResponseMapper;
import backend.spring.SNS.model.dto.response.PostResponse;
import backend.spring.SNS.model.dto.request.CommentWriteRequest;
import backend.spring.SNS.model.dto.request.PostUpdateRequest;
import backend.spring.SNS.model.entity.Comment;
import backend.spring.SNS.model.dto.request.PostUploadRequest;
import backend.spring.SNS.model.entity.Post;
import backend.spring.SNS.service.SNSService;
import backend.spring.security.utils.SecurityUtil;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class SNSController {

    @Autowired
    private final SecurityUtil securityUtil;
    @Autowired
    private final SNSService SNSService;
    @Autowired
    private final PostResponseMapper postResponseMapper;
    @Autowired
    private final CommentResponseMapper commentResponseMapper;

    // 게시물 업로드
    @PostMapping("")
    public ResponseEntity<Void> uploadPost(@RequestParam("photo") MultipartFile[] photos,
                                        @RequestParam("caption") String caption,
                                        @RequestParam("location") String location) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 업로드 요청 DTO 변환
        PostUploadRequest uploadParam = new PostUploadRequest(photos, caption, location);
        try {
            // 게시물 등록
            SNSService.registerPost(memberId, uploadParam);
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        // 모든 Post 조회
        List<Post> posts = SNSService.getAllPosts();

        // 게시물 응답 DTO 변환
        List<PostResponse> postResponses = postResponseMapper.toPostResponses(memberId, posts);
        return ResponseEntity.ok(postResponses);
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> writeComment(@PathVariable Long postId,
                                          @Valid @RequestBody CommentWriteRequest writeParam) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        try {
            // 게시물 댓글 등록
            SNSService.writeComment( memberId, postId, writeParam);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 댓글 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        try {
            // 게시물 댓글 조회
            List<Comment> comments = SNSService.getComments(postId);

            // 댓글 응답 DTO 변환
            List<CommentResponse> commentResponses = commentResponseMapper.toCommentResponses(comments);
            return ResponseEntity.ok(commentResponses);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        try {
            // 게시물 좋아요 등록
            SNSService.likePost(memberId, postId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 좋아요 취소
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();

        try {
            // 게시물 좋아요 취소
            SNSService.unlikePost(memberId, postId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        try {
            // 게시물 단일 조회
            Post post = SNSService.getPostById(postId);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postId, @RequestBody PostUpdateRequest updateParam) {
        try {
            // 게시물 수정
            SNSService.modifyPost(postId, updateParam);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removePost(@PathVariable Long postId) {
        try {
            // 게시물 삭제
            SNSService.removePost(postId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}