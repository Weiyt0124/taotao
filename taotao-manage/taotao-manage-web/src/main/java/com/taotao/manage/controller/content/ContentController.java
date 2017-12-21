package com.taotao.manage.controller.content;

import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.pojo.content.Content;
import com.taotao.manage.service.content.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/21
 */
@RequestMapping("content")
@Controller
public class ContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addContent(Content content) {
        try {
            contentService.save(content);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<EasyUIResult> queryContentList(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "20") Integer rows
    ) {
        try {
            EasyUIResult easyUIResult = contentService.queryEasyUIResultByCategoryId(categoryId, page, rows);
            return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
