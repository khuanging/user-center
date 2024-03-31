package com.zst.usercenter.constant;/**
 * @author zst
 * @date 2024/3/17
 * @Description:
 * @Modified By:
 */

/**
 * @description:
 * @author: ZST
 * @time: 2024/3/17 23:17
 */
public class UserConstant {
    public final class Constants {
        public static final int MIN_ACCOUNT_LENGTH = 4;
        public static final int MIN_PASSWORD_LENGTH = 8;

        public static final int MAX_PLANET_CODE_LENGTH = 5;

        public static final String SALT = "MDS";

        /**
         * 用户登录态的健
         */
        public static final String USER_LOGIN_STATE = "userLoginState";

        /**
         * 管理员
         */
        public static final int ROLE_MANAGER = 1;
        /**
         * 普通用户
         */
        public static final int ROLE_DEFAULT = 0;
    }
}
