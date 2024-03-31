package com.zst.usercenter.model.domain.request;/**
 * @author zst
 * @date 2024/3/18
 * @Description:
 * @Modified By:
 */

import java.io.Serializable;

/**
 * @description: 用户注册请求体
 * @author: ZST
 * @time: 2024/3/18 21:28
 */
public class UserLoginRequest implements Serializable {

    private static final long serialVersionID = 24153612351532324L;

    private String userAccount;

    private String userPassword;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }


}
