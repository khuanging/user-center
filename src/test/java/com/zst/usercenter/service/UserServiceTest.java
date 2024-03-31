package com.zst.usercenter.service;

import java.time.LocalDateTime;

import com.zst.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zst
 * @date 2024/3/16
 * @Description: 用户服务测试
 * @Modified By:
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setUserAccount("123");
        user.setAvatarUrl("https://baomidou.com/img/logo.svg");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("111");
        user.setEmail("222@");
        boolean result = userService.save(user);

        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "";
        String userPassword = "12345678";
        String checkPassword = "12345678";
//        long id = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
//        Assertions.assertEquals(-1, id);
//        userAccount = "aa";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, id);
//        userAccount = "aaaab";
//        userPassword = "1234";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, id);
//        userAccount = "aaaa b!";
//        userPassword = "12345678";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, id);
//        checkPassword = "12344566";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, id);
//        userAccount = "aaaab";
//        userPassword = "12344566";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, id);
//        userAccount = "aaaab22";
//        userPassword = "12344566";
//        id = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertTrue(id > 0);
//        System.out.println(id);

    }
}