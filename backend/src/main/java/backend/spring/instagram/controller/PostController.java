package backend.spring.instagram.controller;

import backend.spring.security.utils.SecurityUtil;
import backend.spring.instagram.model.dto.PostUpdateDto;
import backend.spring.instagram.model.dto.PostUploadDto;
import backend.spring.instagram.model.entity.Post;
import backend.spring.instagram.service.PostService;
import backend.spring.security.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final SecurityService securityService;
    @Autowired
    private final SecurityUtil securityUtil;

    // 게시물 업로드
    @PostMapping("")
    public ResponseEntity<Void> uploadPost(HttpServletRequest request,
                                           @RequestParam("photo") MultipartFile[] photos,
                                           @RequestParam("caption") String caption,
                                           @RequestParam("location") String location) {

        //System.out.println(securityUtil.getCurrentMemberId());

        String token = securityService.resolveToken(request);
        String username = securityService.getUsernameFromToken(token);

        PostUploadDto uploadParam = new PostUploadDto(username, photos, caption, location);
        try {
            postService.registerPost(uploadParam);
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    // 게시물 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 게시물 정보 수정
    @PutMapping("/{postId}")
    public ResponseEntity<?> modifyPost(@PathVariable Long postId, @RequestBody PostUpdateDto updateParam) {
        //postService.modifyPost(postId, updateParam);
        return ResponseEntity.ok().body("게시물 수정 완료");
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> removePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return ResponseEntity.ok().body("게시물 삭제 성공");
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return ResponseEntity.ok().body("좋아요 성공");
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId) {
        postService.unlikePost(postId);
        return ResponseEntity.ok().body("좋아요 취소 성공");
    }

}