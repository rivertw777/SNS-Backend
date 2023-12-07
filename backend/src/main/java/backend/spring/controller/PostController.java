package backend.spring.controller;

import backend.spring.config.annotation.TokenRequired;
import backend.spring.model.post.dto.PostUpdateDto;
import backend.spring.model.post.dto.PostUploadDto;
import backend.spring.model.post.entity.Post;
import backend.spring.service.PostService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    @Value("${file.dir}")
    private String uploadPath;

    //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //System.out.println(authentication);
    //String loggedInUserName = authentication.getName();

    //@TokenRequired
    @PostMapping("")
    public ResponseEntity<Void> uploadPost(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestParam("photo") MultipartFile[] photos,
                                           @RequestParam("caption") String caption,
                                           @RequestParam("location") String location) {

        PostUploadDto uploadParam = new PostUploadDto(photos, caption, location);
        try {
            postService.registerPost(uploadParam, "taewon");
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