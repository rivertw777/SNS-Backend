package backend.spring.service.impl;

import backend.spring.repository.PostDao;
import backend.spring.model.dto.PostUpdateDto;
import backend.spring.model.entity.Post;
import backend.spring.service.PostService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostDao postDao;

    @Override
    public void registerPost(Post post) {
        postDao.save(post);
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
