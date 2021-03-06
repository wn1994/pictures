package com.pictures.service.impl;

import com.pictures.aop.ServiceRedisAop;
import com.pictures.cache.annotation.CacheEvict;
import com.pictures.cache.annotation.Cacheable;
import com.pictures.dao.PictureDao;
import com.pictures.dao.UserDao;
import com.pictures.entity.Picture;
import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;
import com.pictures.exception.BaseException;
import com.pictures.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Service
public class PictureServiceImpl implements PictureService {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceRedisAop.class);
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private UserDao userDao;

    @Override
    @CacheEvict(key = "getPicturesByUserId", fieldKey = "#picture.userId")
    public ResultEnum insertPicture(Picture picture, MultipartFile imageFile) {
        String contentType = imageFile.getContentType();
        // 如果不是图片，抛出格式错误
        if (!contentType.startsWith("image")) {
            throw new BaseException(ResultEnum.FILE_FORMAT_ERROR.getMsg());
        }
        // 生成uuid作为文件名称(保证名字唯一性)
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获得文件后缀名称
        String imageSuffix = contentType.substring(contentType.indexOf("/") + 1);
        // 获取文件时请求的url地址
        String imgUrl = "/img/render/" + uuid + "." + imageSuffix;
        // 图片存储位置
        Path filePath = Paths.get(picture.getPath(), "/resources/images", uuid + "." + imageSuffix);
        // 将文件对应的url地址存入path，前端直接请求该url获取图片
        picture.setPath(imgUrl);
        // 将图片存入文件
        try {
            imageFile.transferTo(filePath.toFile());
        } catch (Exception e) {
            throw new BaseException(ResultEnum.FILE_PATH_ERROR.getMsg());
        }
        int resCode = pictureDao.insertPictureByUserId(picture.getUserId(), picture.getName(), picture.getPath(), picture.isGuestVisible());
        if (resCode == 1) {
            return ResultEnum.INSERT_PICTURE_SUCCESS;
        } else {
            throw new BaseException(ResultEnum.DB_INSERT_RESULT_ERROR.getMsg());
        }
    }

    @Override
    @Cacheable(key = "getPictureById", fieldKey = "#id")
    public Picture getPicture(long id) {
        Picture picture = pictureDao.queryById(id);
        return picture;
    }

    @Override
    @CacheEvict(key = "getPictureById", fieldKey = "#picture.id")
    public ResultEnum updatePicture(Picture picture) {
        try {
            pictureDao.updatePictureById(picture.getId(), picture.getName(), picture.isGuestVisible());
            return ResultEnum.UPDATE_PICTURE_SUCCESS;
        } catch (Exception e) {
            throw new BaseException(ResultEnum.DB_UPDATE_RESULT_ERROR.getMsg());
        }
    }

    @Override
    @CacheEvict(key = "getPictureById", fieldKey = "#id")
    public ResultEnum deletePicture(long id) {
        if (pictureDao.deletePictureById(id) == 1) {
            return ResultEnum.DELETE_PICTURE_SUCCESS;
        } else {
            throw new BaseException(ResultEnum.DB_DELETE_RESULT_ERROR.getMsg());
        }
    }

    @Override
    @Cacheable(key = "getPicturesByUserId", fieldKey = "#userId", isList = true, contentClass = "com.pictures.entity.Picture")
    public List<Picture> listPictures(long userId) {
        List<Picture> pictures = pictureDao.queryByUserId(userId);
        return pictures;
    }

    @Override
    @Cacheable(key = "getPicturesByPhoneNum", fieldKey = "#phoneNum", isList = true, contentClass = "com.pictures.entity.Picture")
    public List<Picture> listGuestPictures(String phoneNum) {
        User user = userDao.queryByPhoneNum(phoneNum);
        return pictureDao.queryByUserIdGuest(user.getId());
    }
}