package backend.spring.repository.impl;

import backend.spring.repository.UserDao;
import backend.spring.model.dto.UserUpdateDto;
import backend.spring.model.entity.User;
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
public class UserRepository implements UserDao {

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
    public Optional<User> findByUserName(String userName) {
        String jpql = "select u from User u where u.userName = :userName";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("userName", userName);
        List<User> resultList = query.getResultList();
        return resultList.stream().findFirst();
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
