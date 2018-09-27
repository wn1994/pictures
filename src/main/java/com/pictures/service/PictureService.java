package com.pictures.service;

import com.pictures.entity.Picture;
import com.pictures.enums.ResultEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    ResultEnum insertPicture(Picture picture, MultipartFile imageFile);

    ResultEnum updatePicture(Picture picture);

    List<Picture> listPictures(long userId);

    List<Picture> listPicturesGuests(String phoneNum);

    Picture getPictureDetail(long pictureId);

    ResultEnum deletePicture(long id);
}