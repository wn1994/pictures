package com.pictures.enums;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常 定义异常时，需要先确定异常所属模块。 例如：无效用户可以定义为 [10010001]
 * 前四位数为系统模块编号，后4位为错误代码 ,唯一。
 */
public enum ResultEnum {
    // 数据库操作异常
    DB_INSERT_RESULT_ERROR(99990001, "db insert error"),
    DB_UPDATE_RESULT_ERROR(99990002, "db update error"),
    DB_DELETE_RESULT_ERROR(99990003, "db delete error"),
    DB_SELECTONE_IS_NULL(99990004, "db select return null"),

    // 系统异常
    INNER_ERROR(99980001, "系统错误"),
    TOKEN_IS_ILLICIT(99980002, "Token验证非法"),
    SESSION_IS_OUT_TIME(99980003, "会话超时"),

    //其他异常
    FILE_FORMAT_ERROR(99970001,"文件格式有误"),
    FILE_PATH_ERROR(99970002,"文件路径有误"),

    // 用户相关
    INSERT_USER_SUCCESS(10010001, "用户插入成功"),
    USER_EXIST(10010002, "用户已存在"),
    USER_NOT_EXIST(10010003, "用户不存在"),
    UPDATE_USER_SUCCESS(10010004, "用户更新成功"),
    DELETE_USER_SUCCESS(10010005, "用户删除成功"),

    // 照片相关
    INSERT_PICTURE_SUCCESS(10021001, "照片插入成功"),
    UPDATE_PICTURE_SUCCESS(10021002, "照片更新成功"),
    DELETE_PICTURE_SUCCESS(10021003, "照片删除成功");


    private int state;
    private String msg;

    ResultEnum(int state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public int getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public static ResultEnum stateOf(int index) {
        for (ResultEnum resultEnum : values()) {
            if (resultEnum.getState() == index) {
                return resultEnum;
            }
        }
        return null;
    }
}
