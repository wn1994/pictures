package com.pictures.dao;

import com.pictures.BaseTest;
import com.pictures.entity.Picture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PictureDaoTest extends BaseTest {
    @Autowired
    PictureDao pictureDao;

    @Test
    public void testQueryById() throws Exception {
        System.out.println("testQueryById");
        int id = 1;
        Picture picture = pictureDao.queryById(id);
        System.out.println(picture);
    }

    @Test
    public void testQueryByUserId() throws Exception {
        System.out.println("testQueryByUserId");
        int userId = 1;
        List<Picture> pictures = pictureDao.queryByUserId(userId);
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }

    @Test
    public void testInsertPictureByUserId() throws Exception {
        System.out.println("testInsertPictureByUserId");
        long userId = 1;
        String name = "测试";
        String path = "/img/render/52b6c7a43c2b4c44ad29717c08abd81e.jpeg";
        int resCode = pictureDao.insertPictureByUserId(userId, name, path, true);
        System.out.println(resCode);
    }
}