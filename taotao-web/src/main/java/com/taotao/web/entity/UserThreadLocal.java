package com.taotao.web.entity;


public class UserThreadLocal {

	private static final ThreadLocal<User> THREAD_LOCAL = new ThreadLocal<User>();
	
	public static void setUser(User user){
		THREAD_LOCAL.set(user);
	}
	
	public static User getUser(){
		return THREAD_LOCAL.get();
		
	}
}
