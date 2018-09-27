package com.pictures.dao;

import com.pictures.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureDao {
    /**
     * 通过图片id查询，并且返回图片实体
     *
     * @param id
     * @return  根据图片id查询单独的图片
     */
    Picture queryById(long id);

    /**
     * 通过该用户id查询，并且返回所有图片
     *
     * @param userId
     * @return  查询到的所有照片
     */
    List<Picture> queryByUserId(long userId);

    /**
     * 通过该用户id查询，并且返回所有图片
     *
     * @param userId
     * @return  查询到的所有照片
     */
    List<Picture> queryByUserIdGuest(long userId);

    /**
     * 插入照片
     *
     * @param userId
     * @param name
     * @param path
     * @param guestVisible
     * @return 插入的行数
     */
    int insertPictureByUserId(@Param("userId") long userId, @Param("name") String name, @Param("path") String path, @Param("guestVisible") boolean guestVisible);

    /**
     * 插入照片
     *
     * @param id
     * @param name
     * @param guestVisible
     * @return 更新的行数
     */
    int updatePictureById(@Param("id") long id, @Param("name") String name, @Param("guestVisible") boolean guestVisible);

    /**
     * 删除照片
     *
     * @param id
     * @return 插入的行数
     */
    int deletePictureById(@Param("id") long id);

}
