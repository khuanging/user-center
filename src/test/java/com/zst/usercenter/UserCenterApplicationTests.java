package com.zst.usercenter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    void testDigest() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(("abcd" + "mypassword").getBytes());
        System.out.println(md5DigestAsHex);
    }

}
