package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.jedis.JedisService;
import com.taotao.web.service.ItemService;

@RequestMapping("/api/item/cache")
@Controller
public class ApiItemCacheController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping("{itemId}")
	public ResponseEntity<Void> cleanItemCache(@PathVariable("itemId")Long itemId){
		try {
			this.itemService.cleanItemCache(itemId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
