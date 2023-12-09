package backend.spring.post.service.impl;

import backend.spring.post.model.dto.PostUploadDto;
import backend.spring.member.model.entity.Member;
import backend.spring.post.model.dto.PostUpdateDto;
import backend.spring.post.model.entity.Post;
import backend.spring.post.repository.PostDao;
import backend.spring.member.repository.MemberRepository;
import backend.spring.post.service.PostService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostDao postDao;
    @Autowired
    private final MemberRepository userDao;

    @Value("${file.dir}")
    private String uploadPath;

    @Override
    public void registerPost(PostUploadDto uploadParam, String username) throws IOException {
        // 사진 경로 반환
        List<String> photoPaths = savePhotos(uploadParam.photos());

        // 작성자 반환
        Member user = userDao.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 게시물 모델 생성
        Post post = Post.create(user, photoPaths, uploadParam);
        postDao.save(post);
    }

    private List<String> savePhotos(MultipartFile[] photos) throws IOException{
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile photo : photos) {
            String projectPath = System.getProperty("user.dir") + uploadPath;
            String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
            File saveFile = new File(projectPath, fileName);
            photo.transferTo(saveFile);
            fileUrls.add(saveFile.getPath());
        }
        return fileUrls;
    }

    @Override
    public List<Post> getAllPosts() {
        return postDao.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postDao.findById(postId);
    }

    @Override
    public void modifyPost(Long postId, PostUpdateDto updateParam) {
        postDao.update(postId, updateParam);
    }

    @Override
    public void removePost(Long postId) {
        postDao.delete(postId);
    }

}
