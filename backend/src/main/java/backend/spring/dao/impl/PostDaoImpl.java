package backend.spring.dao.impl;

import backend.spring.dao.PostDao;
import backend.spring.dao.dto.PostUpdateDto;
import backend.spring.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Transactional
@RequiredArgsConstructor
@Repository
public class PostDaoImpl implements PostDao {

    private final EntityManager em;

    @Override
    public void save(Post post) {
        em.persist(post);
    }

    @Override
    public List<Post> findAll() {
        String jpql = "select i from Post i";
        TypedQuery<Post> query = em.createQuery(jpql, Post.class);
        return query.getResultList();
    }

    @Override
    public Optional<Post> findById(Long postId) {
        Post post = em.find(Post.class, postId);
        return Optional.ofNullable(post);
    }

    @Override
    public void update(Long postId, PostUpdateDto updateParam) {
        Post findPost = em.find(Post.class, postId);
        findPost.setAuthor(updateParam.author());
        findPost.setLocation(updateParam.location());
        findPost.setCaption(updateParam.caption());
    }

    @Override
    public void delete(Long postId) {
        Post post = em.find(Post.class, postId);
        em.remove(post);
    }

}
