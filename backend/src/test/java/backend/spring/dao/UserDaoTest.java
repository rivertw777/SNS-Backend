package backend.spring.dao;

import backend.spring.model.User;
import org.junit.jupiter.api.Assertions;
import backend.spring.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testGetAllUsers() {
        List<User> users = userDao.getAllUsers();
        Assertions.assertTrue(users.size()>0);
    }

    @Test
    public void testGetUserByUserId() {
        User user = userDao.getUserByUserId("testId1");
        Assertions.assertTrue(user.getUserNo() > 0);
    }

    @Test
    public void testInsertUser() {
        User user = new User(0, "testName", "testId", "testPwd");
        User test = userDao.insertUser(user);
    }

    @Test
    public void testUpdateUser() {
        //Todo
    }

    @Test
    public void testDeleteUser() {
        //Todo
    }

}