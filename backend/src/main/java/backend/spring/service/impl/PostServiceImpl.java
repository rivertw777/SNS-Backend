package backend.spring.service.impl;

import backend.spring.model.post.dto.PostUploadDto;
import backend.spring.model.user.entity.User;
import backend.spring.repository.PostDao;
import backend.spring.model.post.dto.PostUpdateDto;
import backend.spring.model.post.entity.Post;
import backend.spring.repository.UserDao;
import backend.spring.service.PostService;
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
    private final UserDao userDao;

    @Value("${file.dir}")
    private String uploadPath;

    @Override
    public void registerPost(PostUploadDto uploadParam, String username) throws IOException {
        // 사진 경로 반환
        List<String> photoPaths = savePhotos(uploadParam.photos());

        // 작성자 반환
        User user = userDao.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

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
