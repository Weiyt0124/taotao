package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {

		/**
		 * @Value("${key}") ：获取当前容器里的某个值
		 * 
		 * 
		 */
	    @Value("${IMAGE_UPLOAD_PATH}")
	    public String IMAGE_UPLOAD_PATH ;
	    @Value("${IMAGE_URL}")
	    public String IMAGE_URL ;
}
