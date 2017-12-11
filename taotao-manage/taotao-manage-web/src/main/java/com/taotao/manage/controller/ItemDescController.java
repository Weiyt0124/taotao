package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("item/desc")
@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;
    private static final Logger LOOGER = LoggerFactory.getLogger(ItemDescController.class);


    /**
     * @param
     * @return
     * @Author Wyt
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable("itemId") Long itemId) {
        try {
            ItemDesc result = itemDescService.queryById(itemId);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
