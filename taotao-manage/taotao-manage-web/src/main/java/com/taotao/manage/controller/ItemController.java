package com.taotao.manage.controller;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("item")
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	private static final Logger LOOGER = LoggerFactory.getLogger(ItemController.class);
	
	/**
	 * 新增商品 
	 * 
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> addItem(Item item,@RequestParam("desc") String desc){
		
		if(LOOGER.isDebugEnabled()){
			LOOGER.debug("新增商品数据  Item = {},desc = {}", item,desc);
		}
		try {
			Boolean flag = this.itemService.saveItemAndDesc(item, desc);
			
			//商品和商品描述都保存成功，return 201
			if(flag){
				if(LOOGER.isInfoEnabled()){
					LOOGER.info("新增商品成功 Item = {},desc = {}", item,desc);
				}
			
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			
			if(LOOGER.isInfoEnabled()){
				LOOGER.info("新增商品失败 Item = {},desc = {}", item,desc);
			}
			
		} catch (Exception e) {
			LOOGER.error("新增商品失败 Item = {},desc = {}", item,desc);
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
