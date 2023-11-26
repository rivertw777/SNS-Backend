package backend.spring.service;

import backend.spring.Application;
import backend.spring.model.User;
import org.junit.jupiter.api.Assertions;
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
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        List<User> users= userService.getAllUsers();
        Assertions.assertTrue(users.size()>0);
    }

    @Test
    public void testGetUserByUserId() {
        User user = userService.getUserByUserId("testId1");
        Assertions.assertTrue(user.getUserNo() > 0);
    }

    @Test
    public void testRegisterUser() {
        User user = new User(0, "testName", "testId", "testPwd");
        User test = userService.registerUser(user);
    }

    @Test
    public void testModifyUser() {
        //Todo
    }

    @Test
    public void testRemoveUser() {
        //Todo
    }

}
