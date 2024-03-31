package com.zst.usercenter.service;

import com.zst.usercenter.constant.UserConstant;
import com.zst.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;


/**
 * @author ZST
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-03-16 23:36:54
 */
public interface UserService extends IService<User> {

    String USER_LOGIN_STATE = UserConstant.Constants.USER_LOGIN_STATE;

    /**
     * 用户注册
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 用户
     * @return User
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     */
    int userLogout(HttpServletRequest request);
}
