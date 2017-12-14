package com.taotao.web.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.httpclient.ApiService;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.common.jedis.JedisService;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.pojo.ItemParamItem;
import com.taotao.web.entity.Item;

@Service
public class ItemService {

	@Value("${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;

	@Autowired
	private ApiService apiService;
	@Autowired
	private JedisService jedisService;

	public final String TAOTAO_ITEM_CACHE = "TAOTAO_ITEM_CACHE";
	private final String TAOTAO_ITEMDESC_CACHE = "TAOTAO_ITEMDESC_CACHE";
	private final String TAOTAO_ITEMPARAMITEM_CACHE = "TAOTAO_ITEMPARAMITEM_CACHE";
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public Item queryItemById(Long itemId) {
		String key = TAOTAO_ITEM_CACHE + itemId;
		try {
			String itemData = jedisService.get(key);
			if (StringUtils.isNotEmpty(itemData)) {
				return MAPPER.readValue(itemData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String uri = TAOTAO_MANAGE_URL + "/rest/web/item/" + itemId;
		try {
			HttpResult data = this.apiService.doGet(uri);
			if (data.getHttpStatus() == 200) {
				String jsonData = data.getData();
				this.jedisService.set(key, jsonData, 60*60*12);
				return MAPPER.readValue(jsonData, Item.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ItemDesc queryItemDescById(Long itemId) {
		String key = TAOTAO_ITEMDESC_CACHE + itemId;
		try {
			String itemDescData = jedisService.get(key);
			if (StringUtils.isNotEmpty(itemDescData)) {
				return MAPPER.readValue(itemDescData, ItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String uri = TAOTAO_MANAGE_URL + "/rest/web/item/itemDesc/" + itemId;
		HttpResult httpResult;
		try {
			httpResult = this.apiService.doGet(uri);
			if (httpResult.getHttpStatus() == 200) {
				String jsonData = httpResult.getData();
				this.jedisService.set(key, jsonData, 60*60*12);
				return MAPPER.readValue(jsonData, ItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryItemParamItemById(Long itemId) {
		
		String key = TAOTAO_ITEMPARAMITEM_CACHE + itemId;
		try {
			String itemParamItemData = jedisService.get(key);
			if (StringUtils.isNotEmpty(itemParamItemData)) {
				return itemParamItemData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String uri = TAOTAO_MANAGE_URL + "/rest/web/item/itemParamItem/"
				+ itemId;
		try {
			HttpResult httpResult = this.apiService.doGet(uri);
			if (httpResult.getHttpStatus() == 200) {
				String dataJson = httpResult.getData();
				ItemParamItem itemParamItem = MAPPER.readValue(dataJson,
						ItemParamItem.class);
				String paramData = itemParamItem.getParamData();
				ArrayNode nodes = (ArrayNode) MAPPER.readTree(paramData);
				StringBuffer stringBuffer = new StringBuffer();
				// 封装table标签
				stringBuffer = stringBuffer
						.append("<table class='Ptable' border='0' cellpadding='0' cellspacing='1' width='100%'><tbody>>");
				for (JsonNode jsonNode : nodes) {
					stringBuffer.append("<tr><th class='tdTitle' colspan='2'>");
					stringBuffer.append(jsonNode.get("group") + "</th>");
					ArrayNode node = (ArrayNode) jsonNode.get("params");
					for (JsonNode jsonNode2 : node) {
						stringBuffer.append("<tr>");
						stringBuffer.append("<td class='tdTitle'>");
						stringBuffer.append(jsonNode2.get("k").asText()
								+ "</td>");
						stringBuffer.append("<td>"
								+ jsonNode2.get("v").asText() + "</td>");
						stringBuffer.append("</tr>");
					}
				}
				stringBuffer.append("/tbody></table>");
				String itemParamItemData = stringBuffer.toString();
				this.jedisService.set(key, itemParamItemData, 60*60*12);
				return itemParamItemData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void cleanItemCache(Long itemId) {
		this.jedisService.del(TAOTAO_ITEM_CACHE+itemId);
		this.jedisService.del(TAOTAO_ITEMDESC_CACHE+itemId);
		this.jedisService.del(TAOTAO_ITEMPARAMITEM_CACHE+itemId);
	}

}
