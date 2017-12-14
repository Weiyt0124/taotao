package com.taotao.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.entity.Item;
import com.taotao.web.entity.Order;
import com.taotao.web.entity.User;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserServcie;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private ItemService itemServcie;
	@Autowired
	private OrderService orderService;
	@Autowired
	private UserServcie userService;
	
	/**
	 * 添加商品到购物车
	 * 之前要在拦截器中配置，判断用户是否为登录状态
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/add/{itemId}")
	public ModelAndView addItemToCart(@PathVariable("itemId")Long itemId){
		ModelAndView mv = new ModelAndView("order");
		Item item = this.itemServcie.queryItemById(itemId);
		mv.addObject("item",item);
		return mv;
	}
	
	
	@ResponseBody
	@RequestMapping("/submit")
	public Map<String,Object> submitOrder(Order order){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String orderId = this.orderService.createOrder(order);
		if(StringUtils.isNotBlank(orderId)){
			map.put("status", 200);
			map.put("data",orderId);
			return map;
		}
		map.put("status", 500);
		return map;
	}
	
	@RequestMapping("/success")
	public ModelAndView submitOrderSuccess(@RequestParam("id")Long orderId){
		Order order = this.orderService.queryOrderByOrderId(orderId);
		ModelAndView mv = new ModelAndView("success");
		mv.addObject("order", order);
		DateTime dateTime = new DateTime();
		String date = dateTime.plusDays(2).toString("MM月dd天");
		mv.addObject("date",date);
		return mv;
	}
}
