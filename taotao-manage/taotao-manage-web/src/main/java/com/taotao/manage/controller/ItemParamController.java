package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/14
 */
@Controller
@RequestMapping("item/param")
public class ItemParamController {
    @Autowired
    ItemParamService itemParamService;

    /**
     * 查询商品规格
     *
     * @param
     * @return
     * @Author Wyt
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.GET)
    public ResponseEntity<ItemParam> queryItemParamByItemCatId(@PathVariable("itemCatId") Long itemCatId) {

        try {
            ItemParam record = new ItemParam();
            record.setItemCatId(itemCatId);
            ItemParam itemParam = itemParamService.queryOne(record);
            if (itemParam == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(itemParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    /**
     * 保存商品规格
     *
     * @param
     * @return
     * @Author Wyt
     */
    @RequestMapping(value = "{itemCatId}", method = RequestMethod.POST)
    public ResponseEntity<Void> saveItemParam(@PathVariable("itemCatId") Long itemCatId, @RequestParam("paramData") String paramData) {
        try {
            if (itemCatId == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            ItemParam record = new ItemParam();
            record.setId(null);
            record.setItemCatId(itemCatId);
            record.setParamData(paramData);
            itemParamService.save(record);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
