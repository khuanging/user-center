package com.zst.usercenter.controller;/**
 * @author zst
 * @date 2024/3/18
 * @Description:
 * @Modified By:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zst.usercenter.common.BaseResponse;
import com.zst.usercenter.common.ErrorCode;
import com.zst.usercenter.common.ResultUtils;
import com.zst.usercenter.exception.BusinessException;
import com.zst.usercenter.model.domain.User;
import com.zst.usercenter.model.domain.request.UserLoginRequest;
import com.zst.usercenter.model.domain.request.UserRegisterRequest;
import com.zst.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zst.usercenter.constant.UserConstant.Constants.ROLE_MANAGER;
import static com.zst.usercenter.constant.UserConstant.Constants.USER_LOGIN_STATE;

/**
 * @description:
 * @author: ZST
 * @time: 2024/3/18 21:23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long userId = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        if (userId == -1) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) objUser;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "当前用户不存在");
        }
        long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest servletRequest) {
        User user = getLoggedInUser(servletRequest);
        if (user == null || user.getUserRole() != ROLE_MANAGER) {
            throw new BusinessException(ErrorCode.NO_AUTH, "用户未登录或者无权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> users = userService.list(queryWrapper);
        List<User> userList = users.stream()
                .map(user1 -> userService.getSafetyUser(user1))
                .collect(Collectors.toList());
        return ResultUtils.success(userList);
    }

    /**
     * 删除用户
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest servletRequest) {
        User user = getLoggedInUser(servletRequest);
        if (user == null || user.getUserRole() != ROLE_MANAGER) {
            throw new BusinessException(ErrorCode.NO_AUTH, "用户不存在或者无权限");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isSuccess = userService.removeById(id);
        if (!isSuccess) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(isSuccess);
    }

    /**
     * 确认是否为管理员
     *
     * @param servletRequest
     * @return
     */
    private User getLoggedInUser(HttpServletRequest servletRequest) {
        // 1、鉴权，仅管理员可以查询用户
        Object userObj = servletRequest.getSession().getAttribute(UserService.USER_LOGIN_STATE);
        return (User) userObj;
    }
}
