package com.taotao.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.common.utils.CookieUtils;
import com.taotao.web.entity.User;
import com.taotao.web.entity.UserThreadLocal;
import com.taotao.web.service.UserServcie;

public class UserLoginInterceptor implements HandlerInterceptor{
	
	private final String COOKIE_NAME = "TOKEN";
	
	@Autowired
	private UserServcie userServcie;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取cookie
		String cookieValue = CookieUtils.getCookieValue(request, COOKIE_NAME);
		if(StringUtils.isBlank(cookieValue)){
			//未登录,跳转到登录页面
			 response.sendRedirect("http://sso.taotao.com/user/login.html");
			 return false;
		}
		User user = this.userServcie.queryUserByToken(cookieValue);
		if(user == null){
			//未登录,跳转到登录页面
			 response.sendRedirect("http://sso.taotao.com/user/login.html");
			return false;
		}
		//将登录的用户存入本地线程
		UserThreadLocal.setUser(user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
