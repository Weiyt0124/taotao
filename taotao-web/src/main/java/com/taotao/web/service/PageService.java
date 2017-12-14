package com.taotao.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.taotao.common.httpclient.ApiService;
import com.taotao.common.httpclient.HttpResult;

@Service
public class PageService {

	@Autowired
	private ApiService apiService;
	
	@Value(value="${TAOTAO_MANAGE_URL}")
	private String TAOTAO_MANAGE_URL;
	@Value("${INDEX_AD1_URL}")
	private String INDEX_AD1_URL;
	@Value("${INDEX_AD2_URL}")
	private String INDEX_AD2_URL;
	private static final ObjectMapper MAPPER =  new ObjectMapper();
	
	public String queryAd2(){
		String uri = TAOTAO_MANAGE_URL + INDEX_AD2_URL;
		HttpResult httpResult;
		try {
			httpResult = apiService.doGet(uri);
			if(httpResult.getHttpStatus() == 200){
				String jsonData = httpResult.getData();
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				JsonNode node2 = jsonNode.get("rows");
				List<Map<String, Object>> contentList = new ArrayList<Map<String,Object>>();
				for (JsonNode node : node2) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("srcB", node.get("pic2").asText());
					map.put("height", 70);
					map.put("alt", node.get("title").asText());
					map.put("width", 310);
					map.put("src", node.get("pic").asText());
					map.put("widthB",210);
					map.put("href", node.get("url").asText());
					map.put("heightB", 70);
					contentList.add(map);
				}
				return MAPPER.writeValueAsString(contentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryAd1() {
		String uri = TAOTAO_MANAGE_URL + INDEX_AD1_URL;
		HttpResult httpResult;
		try {
			httpResult = apiService.doGet(uri);
			if(httpResult.getHttpStatus() == 200){
				String jsonData = httpResult.getData();
				JsonNode jsonNode = MAPPER.readTree(jsonData);
				ArrayNode arrayNode = (ArrayNode) jsonNode.get("rows");
				List<Map<String, Object>> contentList = new ArrayList<Map<String,Object>>();
				for (JsonNode node : arrayNode) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("srcB", node.get("pic2").asText());
					map.put("height", 240);
					map.put("alt", node.get("title").asText());
					map.put("width", 670);
					map.put("src", node.get("pic").asText());
					map.put("widthB",550);
					map.put("href", node.get("url").asText());
					map.put("heightB", 240);
					contentList.add(map);
				}
				return MAPPER.writeValueAsString(contentList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
