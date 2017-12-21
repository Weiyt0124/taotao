package com.taotao.manage.controller.content;

import com.taotao.manage.pojo.content.ContentCategory;
import com.taotao.manage.service.content.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/20
 */
@RequestMapping("content/category")
@Controller
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {

        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(parentId);
            List<ContentCategory> contentCategories = contentCategoryService.queryListByWhere(contentCategory);
            if (contentCategories == null || contentCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(contentCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> addContentCategory(ContentCategory contentCategory) {
        try {
            Boolean flag = contentCategoryService.addContentCategory(contentCategory);
            if (flag) {
                ResponseEntity.status(HttpStatus.CREATED).body(contentCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategory) {
        try {
            Integer integer = contentCategoryService.update(contentCategory);
            if (integer == 1) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategory) {
        try {
            boolean flag = contentCategoryService.deleteContentCategory(contentCategory);
            if (flag) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
