package backend.spring.instagram.service.impl;

import backend.spring.instagram.model.dto.request.PostUploadRequest;
import backend.spring.member.model.entity.Member;
import backend.spring.instagram.model.entity.Post;
import backend.spring.instagram.repository.PostRepository;
import backend.spring.instagram.service.PostService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    @Value("${photo.dir}")
    private String uploadPath;
    @Value("${photo.url}")
    private String serverUrl;

    // 게시물 등록
    @Override
    public void registerPost(Member member, PostUploadRequest uploadParam) throws IOException {
        // 사진 경로 반환
        List<String> photoUrls = savePhotos(uploadParam.photos());

        // 게시물 모델 생성, 저장
        Post post = Post.create(member, photoUrls.get(0), uploadParam);
        postRepository.save(post);
    }


    // 사진 저장 및 경로 반환
    private List<String> savePhotos(MultipartFile[] photos) throws IOException {
        List<String> photoUrls = new ArrayList<>();
        String projectPath = System.getProperty("user.dir") + uploadPath;

        for (MultipartFile photo : photos) {
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            // 사진 파일 저장
            photo.transferTo(saveFile);

            // post 모델에 담길 경로
            String photoPath = serverUrl + fileName;
            photoUrls.add(photoPath);
        }
        return photoUrls;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void likePost(Member member, Long postId) {
        // 게시물 반환
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));

        // 이미 좋아요한 경우
        Set<Member> likeUserSet = post.getLikeUserSet();
        if (likeUserSet.contains(member)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        // 좋아요 목록에 추가
        post.getLikeUserSet().add(member);
        postRepository.save(post);
    }

    public void unlikePost(Member member, Long postId) {
        // 게시물 반환
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 게시물이 없습니다."));

        // 이미 좋아요 취소한 경우
        Set<Member> likeUserSet = post.getLikeUserSet();
        if (!likeUserSet.contains(member)) {
            throw new IllegalArgumentException("이미 좋아요를 취소했습니다.");
        }

        // 좋아요 목록에서 삭제
        post.getLikeUserSet().remove(member);
        postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    //@Override
    //public void modifyPost(Long postId, PostUpdateDto updateParam) {
    //    postRepository.update(postId, updateParam);
    //}

    @Override
    public void removePost(Long postId) {
        postRepository.deleteById(postId);
    }

}
