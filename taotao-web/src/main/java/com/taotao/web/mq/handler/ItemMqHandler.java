package com.taotao.web.mq.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.jedis.JedisService;
import com.taotao.web.service.ItemService;

public class ItemMqHandler {

	@Autowired
	private JedisService jedisService;
	@Autowired
	private ItemService itemService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public void excute(String Msg){
		
		try {
			JsonNode jsonNode = MAPPER.readTree(Msg);
			int itemId = jsonNode.get("itemId").asInt();
			String key = itemService.TAOTAO_ITEM_CACHE+itemId;
			this.jedisService.del(key);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
