package com.pictures.controller;

import com.pictures.exception.BaseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class FileServerController {
    private String imgRootPath = "C:/Users/DELL/IdeaProjects/pictures/src/main/webapp/resources/images/";

    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/img/render/{uuidFilename:.+}", method = GET)
    public void providePictureByUUID(@PathVariable String uuidFilename, HttpServletResponse response) {
        String imgPath = imgRootPath + uuidFilename;
        String imgType = imgPath.substring(imgPath.lastIndexOf(".") + 1);
        response.setContentType("image/" + imgType);
        try {
            FileInputStream fis = new FileInputStream(new File(imgPath));
            byte[] image = new byte[fis.available()];
            fis.read(image);
            OutputStream out = response.getOutputStream();
            out.write(image);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            throw new BaseException("没有这个文件");
        } catch (IOException e) {
            throw new BaseException("读取文件时出错");
        }
    }
}
