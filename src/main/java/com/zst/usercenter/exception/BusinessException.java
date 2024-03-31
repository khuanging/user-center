package com.zst.usercenter.exception;/**
 * @author zst
 * @date 2024/3/27
 * @Description:
 * @Modified By:
 */

import com.zst.usercenter.common.ErrorCode;

/**
 * @description: 全局异常处理
 * @author: ZST
 * @time: 2024/3/27 22:16
 */
public class BusinessException extends RuntimeException{

    private final int code;

    private final String description;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
