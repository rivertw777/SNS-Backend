package backend.spring.controller;

import backend.spring.dao.dto.PostUpdateDto;
import backend.spring.model.Post;
import backend.spring.service.PostService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping("")
    public ResponseEntity<Void> registerPost(@RequestBody Post post) {
        postService.registerPost(post);
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