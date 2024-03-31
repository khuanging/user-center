package com.zst.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zst.usercenter.common.ErrorCode;
import com.zst.usercenter.exception.BusinessException;
import com.zst.usercenter.service.UserService;
import com.zst.usercenter.model.domain.User;
import com.zst.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zst.usercenter.constant.UserConstant.Constants.*;

/**
 * @author ZST
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-03-16 23:36:54
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1、校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为为空");
        }
        if (userAccount.length() < MIN_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > MAX_PLANET_CODE_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户编号过长");
        }
        // 账号不能有特殊字符
        if (!isValidAccount(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号有特殊字符");
        }
        // 密码和校验码相同
        if (!isPasswordMatch(userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }
        // 账号不能重复
        if (!isUniqueAccount(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册账号已存在");
        }
        if (!isUniqueCode(planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册编码已存在");
        }
        // 2、加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3、插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5Password);
        user.setPlanetCode(planetCode);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "保存数据失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1、校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            // todo 修改为自定义异常
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为为空");
        }
        if (userAccount.length() < MIN_ACCOUNT_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < MIN_PASSWORD_LENGTH) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 账号不能有特殊字符
        if (!isValidAccount(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号有特殊字符");
        }
        // 2、加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", md5Password);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {

            throw new BusinessException(ErrorCode.NULL_ERROR, "用户不存在");
        }

        // 3、用户脱敏,防止数据库字段泄密给前端
        User safetyUser = getSafetyUser(user);
        // 4、记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        return safetyUser;
    }

    private boolean isUniqueAccount(String userAccount) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        return count == 0;
    }

    private boolean isUniqueCode(String planetCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        long count = userMapper.selectCount(queryWrapper);
        return count == 0;
    }

    private boolean isValidAccount(String userAccount) {
        Pattern valuePattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher = valuePattern.matcher(userAccount);
        return matcher.matches();
    }

    private boolean isPasswordMatch(String userPassword, String checkPassword) {
        return userPassword.equals(checkPassword);
    }

    /**
     * 用户信息脱敏方法
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setPlanetCode(originUser.getPlanetCode());

        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




