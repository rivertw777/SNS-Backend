package backend.spring.instagram.controller;

import backend.spring.instagram.model.dto.response.mapper.PostResponseMapper;
import backend.spring.instagram.model.dto.response.PostResponse;
import backend.spring.instagram.model.dto.request.CommentWriteRequest;
import backend.spring.instagram.model.dto.request.PostUpdateRequest;
import backend.spring.instagram.model.entity.Comment;
import backend.spring.instagram.service.CommentService;
import backend.spring.instagram.model.dto.request.PostUploadRequest;
import backend.spring.instagram.model.entity.Post;
import backend.spring.instagram.service.PostService;
import backend.spring.member.model.entity.Member;
import backend.spring.security.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
public class PostController {

    @Autowired
    private final PostService postService;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final PostResponseMapper postResponseMapper;

    //@Autowired
    //private final SecurityUtil securityUtil;
    //System.out.println(securityUtil.getCurrentMemberId()

    // 게시물 업로드
    @PostMapping("")
    public ResponseEntity<?> uploadPost(
                                           @RequestParam("photo") MultipartFile[] photos,
                                           @RequestParam("caption") String caption,
                                           @RequestParam("location") String location) {

        String username = "taewon";

        PostUploadRequest uploadParam = new PostUploadRequest(photos, caption, location);
        try {
            postService.registerPost(username, uploadParam);
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<?> getAllPosts(HttpServletRequest request) {

        String username = "taewon";
        Member member = (Member) securityService.loadUserByUsername(username);
        Long userId = member.getUserId();

        // 모든 Post 조회
        List<Post> posts = postService.getAllPosts();

        // 게시물 dto 반환
        List<PostResponse> postResponses = postResponseMapper.toPostResponses(posts, userId);
        return ResponseEntity.ok(postResponses);
    }


    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> writeComment(@PathVariable Long postId,
                                          @Valid @RequestBody CommentWriteRequest writeParam) {
        String username = "taewon";

        try {
            commentService.writeComment( username, postId, writeParam);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 댓글 조회
    @GetMapping("/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Long postId) {
        try {
            // postId를 사용하여 해당 게시물의 댓글 목록을 조회합니다.
            List<Comment> commentList = commentService.getComments(postId);
            return ResponseEntity.ok(commentList);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 좋아요
    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {

        String username = "taewon";
        try {
            postService.likePost(username, postId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 좋아요 취소
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {

        String username = "taewon";

        try {
            postService.unlikePost(username, postId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    // 게시물 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 게시물 정보 수정
    @PutMapping("/{postId}")
    public ResponseEntity<?> modifyPost(@PathVariable Long postId, @RequestBody PostUpdateRequest updateParam) {
        //postService.modifyPost(postId, updateParam);
        return ResponseEntity.ok().build();
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return ResponseEntity.ok().build();
    }

}