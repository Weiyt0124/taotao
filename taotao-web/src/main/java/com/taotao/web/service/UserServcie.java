package com.taotao.web.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.common.httpclient.ApiService;
import com.taotao.common.httpclient.HttpResult;
import com.taotao.web.entity.User;

@Service
public class UserServcie {

	private final String TAOTAO_SSO_URL = "http://sso.taotao.com/service/user/query/re/";
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ApiService apiService;
	
	public User queryUserByToken(String token){
		String uri = TAOTAO_SSO_URL + token;
		try {
			HttpResult httpResult = this.apiService.doGet(uri);
			if(httpResult == null){
				return null;
			}
			if(httpResult.getHttpStatus() == 200){
				String jsonData = httpResult.getData();
				User user = MAPPER.readValue(jsonData, User.class);
				return user;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
