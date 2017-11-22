package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/11/22
 */
@Controller
@RequestMapping("item")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 首次加载查询时返回所有
     * 但是后续会继续点击叶子节点，查询该大类目下的子类目，所以需要根据点击传入的parentId进行查询
     * 首次进入，初始化查询时没有点击，也就没有参数传入，所以默认加载所有第一季的类目，根据数据库表结构，
     * 顶级目录结构的父级id设计为0，所以设置默认值为 0
     *
     * @return ResponseEntity
     */
    @RequestMapping("/cat/list")
    public ResponseEntity<List<ItemCat>> queryItemCats(@RequestParam(value = "id", defaultValue = "0") Long id) {
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(id);
            List<ItemCat> itemCatList = itemCatService.queryListByWhere(itemCat);
            if (itemCatList == null || itemCatList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(itemCatList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
