package com.zst.usercenter.common;/**
 * @author zst
 * @date 2024/3/26
 * @Description:
 * @Modified By:
 */

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 基本响应类
 * @author: ZST
 * @time: 2024/3/26 23:36
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
