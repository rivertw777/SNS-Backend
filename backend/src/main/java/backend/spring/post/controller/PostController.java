package backend.spring.post.controller;

import backend.spring.config.SecurityConfig;
import backend.spring.config.jwt.TokenProvider;
import backend.spring.post.model.dto.PostUpdateDto;
import backend.spring.post.model.dto.PostUploadDto;
import backend.spring.post.model.entity.Post;
import backend.spring.post.service.PostService;
import backend.spring.security.service.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.StringUtils;
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

    private final SecurityService securityService;


    //@TokenRequired
    @PostMapping("")
    public ResponseEntity<Void> uploadPost(HttpServletRequest request,
                                           @RequestParam("photo") MultipartFile[] photos,
                                           @RequestParam("caption") String caption,
                                           @RequestParam("location") String location) {

        String token = securityService.resolveToken(request);
        String username = securityService.getUsernameFromToken(token);

        PostUploadDto uploadParam = new PostUploadDto(photos, caption, location);
        try {
            postService.registerPost(uploadParam, username);
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> modifyPost(@PathVariable Long postId, @RequestBody PostUpdateDto updateParam) {
        postService.modifyPost(postId, updateParam);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removePost(@PathVariable Long postId) {
        postService.removePost(postId);
        return ResponseEntity.noContent().build();
    }

}