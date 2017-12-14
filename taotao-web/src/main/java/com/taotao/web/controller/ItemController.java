package com.taotao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.manage.pojo.ItemDesc;
import com.taotao.web.entity.Item;
import com.taotao.web.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * 查询商品详细数据
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="{itemId}")
	public ModelAndView queryItemDetails(@PathVariable("itemId")Long itemId){
		ModelAndView mv = new ModelAndView();
		//查询商品信息
		Item item = itemService.queryItemById(itemId);
		mv.addObject("item",item);
		//查询商品描述数据
		ItemDesc itemDesc = itemService.queryItemDescById(itemId);
		mv.addObject("itemDesc",itemDesc);
		//查询商品规格参数
		String itemParamItemHtml = itemService.queryItemParamItemById(itemId);
		mv.addObject("itemParam", itemParamItemHtml);
		mv.setViewName("item");
		return mv;
	}
}
