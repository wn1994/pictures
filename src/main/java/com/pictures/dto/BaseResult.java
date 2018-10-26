package com.pictures.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ajax 请求的返回类型封装JSON结果
 *
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Data
public class BaseResult<T> implements Serializable {
    private boolean success;
    private T data;
    private String msg;

    // 返回所需的数据
    public BaseResult(boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    // 返回成功/失败信息
    public BaseResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
