package com.pictures.controller;

import com.pictures.aop.ServiceRedisAop;
import com.pictures.dto.BaseResult;
import com.pictures.entity.Picture;
import com.pictures.entity.User;
import com.pictures.enums.ResultEnum;
import com.pictures.exception.BaseException;
import com.pictures.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author wangning
 * @date 2018/10/18 11:34
 */

@Controller
@RequestMapping("/user")
public class PictureController {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceRedisAop.class);
    private PictureService pictureService;

    public PictureController() {
        super();
    }

    @Autowired
    public PictureController(PictureService pictureService) {
        super();
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/{phoneNum}/picture", method = GET)
    public String showPictures(@PathVariable String phoneNum, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user.getPhoneNum().equals(phoneNum)) {
            // 访问自己的照片
            List<Picture> pictures = this.pictureService.listPictures(user.getId());
            model.addAttribute("pictures", pictures);
            model.addAttribute("visitSelf", true);
        } else {
            // 访问他人的照片
            List<Picture> pictures = this.pictureService.listGuestPictures(phoneNum);
            model.addAttribute("pictures", pictures);
            model.addAttribute("visitSelf", false);
        }
        return "pictures";
    }

    @ResponseBody
    @RequestMapping(value = "/{phoneNum}/picture", method = POST, produces = {"application/json; charset=utf-8"})
    public BaseResult insertPicture(Picture picture, @RequestParam(value = "img_file", required = false) MultipartFile imageFile,
                                    HttpSession session, HttpServletRequest request) {
        try {
            String pathRoot = request.getSession().getServletContext().getRealPath("");
            // 先将物理路径webapp所在路径放入picturePath中
            picture.setPath(pathRoot);
            // 客户端并不知道userId，需要在服务端从session中取出
            User user = (User) session.getAttribute("user");
            picture.setUserId(user.getId());
            ResultEnum resultEnum = this.pictureService.insertPicture(picture, imageFile);
            return new BaseResult(true, resultEnum.getMsg());
        } catch (BaseException e) {
            return new BaseResult(false, e.getMessage());
        } catch (Exception e) {
            return new BaseResult(false, ResultEnum.INNER_ERROR.getMsg());
        }
    }

    @RequestMapping(value = "/{phoneNum}/picture/{pictureId}", method = GET)
    public String showPictureDetail(@PathVariable String phoneNum, @PathVariable long pictureId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        // 查看url是否是当前用户
        if (phoneNum.equals(user.getPhoneNum())) {
            Picture picture = this.pictureService.getPicture(pictureId);
            if(picture != null) {
                // 如果该图片是当前用户的，才返回
                if (picture.getUserId() == user.getId()) {
                    model.addAttribute("picture", picture);
                    return "pictureDetail";
                }
            }
        }
        throw new BaseException("请求url有误");
    }

    @ResponseBody
    @RequestMapping(value = "/{phoneNum}/picture/{pictureId}", method = {PUT, DELETE},
            produces = {"application/json; charset=utf-8"})
    public BaseResult processPictureDetail(@RequestBody @Valid Picture picture, HttpServletRequest
            request) {
        try {
            ResultEnum resultEnum;
            if ("PUT".equals(request.getMethod())) {
                resultEnum = this.pictureService.updatePicture(picture);
            } else {
                resultEnum = this.pictureService.deletePicture(picture.getId());
            }
            return new BaseResult(true, resultEnum.getMsg());
        } catch (BaseException e) {
            return new BaseResult(false, e.getMessage());
        } catch (Exception e) {
            return new BaseResult(false, ResultEnum.INNER_ERROR.getMsg());
        }
    }
}
