package com.taotao.manage.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.pojo.item.ItemCatResult;
import com.taotao.manage.service.item.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/20
 */
@Controller
@RequestMapping("api/item/cat")
public class ApiItemCatController {
    @Autowired
    ItemCatService itemCatService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public ResponseEntity<String> queryItemCatList(@RequestParam(value = "callback", required = false) String callback) {
        try {
            ItemCatResult itemCatResult = itemCatService.queryAllToTree();
            String itemCatResultString = MAPPER.writeValueAsString(itemCatResult);
            if (StringUtils.isEmpty(callback)) {
                return ResponseEntity.ok(itemCatResultString);
            } else {
                //不为空说明需要跨域，于是将函数名拼接在前面
                return ResponseEntity.ok(callback + "(" + itemCatResultString + ");");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}
