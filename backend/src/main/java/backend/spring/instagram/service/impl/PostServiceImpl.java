package backend.spring.instagram.service.impl;

import backend.spring.instagram.model.dto.PostUploadDto;
import backend.spring.member.model.entity.Member;
import backend.spring.instagram.model.entity.Post;
import backend.spring.member.repository.MemberRepository;
import backend.spring.instagram.repository.PostRepository;
import backend.spring.instagram.service.PostService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final MemberRepository memberRepository;

    @Value("${photo.dir}")
    private String uploadPath;
    @Value("${photo.url}")
    private String serverUrl;

    @Override
    public void registerPost(PostUploadDto uploadParam) throws IOException {
        // 사진 경로 반환
        List<String> photoUrls = savePhotos(uploadParam.photos());

        // 작성자 반환
        Member user = memberRepository.findByUsername(uploadParam.username()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + uploadParam.username()));

        // 게시물 모델 생성
        Post post = Post.create(user, photoUrls.get(0), uploadParam);
        postRepository.save(post);
    }

    private List<String> savePhotos(MultipartFile[] photos) throws IOException{
        List<String> photoUrls = new ArrayList<>();
        String projectPath = System.getProperty("user.dir") + uploadPath;
        for (MultipartFile photo : photos) {
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
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

    @Override
    public void likePost(Long postId) {

    }

    @Override
    public void unlikePost(Long postId) {

    }

}
