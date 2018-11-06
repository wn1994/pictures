package com.pictures.service.impl;

import com.pictures.BaseTest;
import com.pictures.entity.Picture;
import com.pictures.service.PictureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PictureServiceImplTest extends BaseTest {
    @Autowired
    private PictureService pictureService;

    @Test
    public void testListPictures() {
        System.out.println("testListPictures");
        List<Picture> pictures = pictureService.listPictures(1);
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }

    @Test
    public void testUpdatePicture() {
        System.out.println("testUpdatePicture");
        Picture picture = new Picture();
        picture.setId(5);
        picture.setName("qqqq");
        picture.setGuestVisible(true);
        System.out.println(pictureService.updatePicture(picture).getMsg());
    }

    @Test
    public void testListGuestPictures() {
        System.out.println("testListGuestPictures");
        String phoneNum = "18810000000";
        List<Picture> pictures = pictureService.listGuestPictures(phoneNum);
        for (Picture picture : pictures) {
            System.out.println(picture);
        }
    }

    @Test
    public void testGetPicture() {
        System.out.println("getPicture");
        long pictureId = 1;
        Picture picture = pictureService.getPicture(pictureId);
        System.out.println(picture);
    }
}
