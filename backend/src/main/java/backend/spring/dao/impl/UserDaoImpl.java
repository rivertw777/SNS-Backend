package backend.spring.dao.impl;

import backend.spring.dao.UserDao;
import backend.spring.dao.dto.UserUpdateDto;
import backend.spring.model.User;
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
public class UserDaoImpl implements UserDao {

    private final EntityManager em;

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public List<User> findAll() {
        String jpql = "select i from User i";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        return query.getResultList();
    }

    @Override
    public Optional<User> findById(Long userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public void update(Long userId, UserUpdateDto updateParam) {
        User findUser = em.find(User.class, userId);
        findUser.setUserName(updateParam.userName());
        findUser.setUserPassword(updateParam.userPassword());
    }

    @Override
    public void delete(Long userId) {
        User user = em.find(User.class, userId);
        em.remove(user);
    }

}
