package com.taotao.web.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.ApiService;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.web.entity.Order;
import com.taotao.web.entity.User;
import com.taotao.web.entity.UserThreadLocal;

@Service
public class OrderService {

	@Autowired
	private ApiService apiService;
	
	private String Order_URL = "http://order.taotao.com/order/query/";

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public String createOrder(Order order) {
		String jsonData;
		try {
			User user = UserThreadLocal.getUser();
			order.setUserId(user.getId());
			order.setStatus(1);
			order.setCreateTime(new Date());
			order.setUpdateTime(order.getCreateTime());
			order.setBuyerNick(user.getUsername());
			jsonData = MAPPER.writeValueAsString(order);
			HttpResult httpResult = this.apiService.doPostJson("http://order.taotao.com/order/create", jsonData);
			if(httpResult.getHttpStatus().intValue() == 200){
				String data = httpResult.getData();
				JsonNode readTree = MAPPER.readTree(data);
				return readTree.get("data").asText();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Order queryOrderByOrderId(Long orderId) {
		String uri = Order_URL + orderId;
		try {
			HttpResult httpResult = this.apiService.doGet(uri);
			if(httpResult.getHttpStatus() == 200){
				return MAPPER.readValue(httpResult.getData(),Order.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
