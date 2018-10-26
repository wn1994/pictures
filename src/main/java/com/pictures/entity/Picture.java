package com.pictures.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Data
public class Picture {
    private long id;
    private String name;
    private Date uploadTime;
    /**
     * 多对一的复合属性（外键）
     */
    private long userId;
    private String path;
    private boolean guestVisible;
}
