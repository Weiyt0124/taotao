package com.taotao.manage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.bean.PicUploadResult;
import com.taotao.manage.service.PropertiesService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/11/22
 */
@Controller
@RequestMapping("pic")
public class PicUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PicUploadController.class);
    /**
     * 前台要求传回json数据，定义json处理对象,将java对象转成json返回
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    PropertiesService propertiesService;
    /**
     * 允许上传的格式
     */
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".gif", ".png"};

    @RequestMapping(value = "upload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletResponse response) throws IOException {
        // 校验图片格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        PicUploadResult fileUploadResult = new PicUploadResult();
        fileUploadResult.setError(isLegal ? 0 : 1);
        String filePath = getFilePath(uploadFile.getOriginalFilename());

        String picUrl = StringUtils.replace(StringUtils.substringAfter(filePath, propertiesService.IMAGE_UPLOAD_PATH),
                "\\", "/");
        fileUploadResult.setUrl(propertiesService.IMAGE_URL + picUrl);
        File newFile = new File(filePath);
        // 写文件到磁盘
        uploadFile.transferTo(newFile);

        // 校验图片是否合法
        isLegal = false;
        try {
            BufferedImage image = ImageIO.read(newFile);
            if (image != null) {
                fileUploadResult.setWidth(image.getWidth() + "");
                fileUploadResult.setHeight(image.getHeight() + "");
                isLegal = true;
            }
        } catch (IOException e) {
        }
        // 状态
        fileUploadResult.setError(isLegal ? 0 : 1);

        if (!isLegal) {
            // 不合法，将磁盘上的文件删除
            newFile.delete();
        }
        //将java对象序列化为json字符串
        return mapper.writeValueAsString(fileUploadResult);
    }

    /**
     * 上传图片的文件夹生成
     *
     * @param
     * @return
     */
    private String getFilePath(String sourceFileName) {
        String baseFolder = propertiesService.IMAGE_UPLOAD_PATH + File.separator + "images";

        //获取年月日
        Calendar now = Calendar.getInstance();
        //获取时间日期 2017012302204444
        Date nowDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
        String dateNowStr = sdf.format(nowDate);

        // yyyy/MM/dd
        String fileFolder = baseFolder + File.separator + now.get(Calendar.YEAR)
                + File.separator + ((now.get(Calendar.MONTH) + 1) + "") + File.separator
                + now.get(Calendar.DAY_OF_MONTH);
        File file = new File(fileFolder);
        if (!file.isDirectory()) {
            // 如果目录不存在，则创建目录
            file.mkdirs();
        }
        // 生成新的文件名
        String fileName = dateNowStr
                + RandomUtils.nextInt(100, 9999) + "." + StringUtils.substringAfterLast(sourceFileName, ".");
        return fileFolder + File.separator + fileName;
    }
}
