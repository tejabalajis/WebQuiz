package com.webq.interceptors;

import java.util.Map;

import org.apache.log4j.Logger;

import com.webq.beans.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginAuthenticationInterceptor implements Interceptor {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void destroy() {
		System.out.println("Destroying MyLoggingInterceptor...");
	}
	
	@Override
	public void init() {
		System.out.println("Initializing  MyLoggingInterceptor...");
	}
	
	@Override
	public String intercept(ActionInvocation actionInvocation) {
		
		try {
			ActionContext context = actionInvocation.getInvocationContext();
			Map<String, Object> session = context.getSession();
			
			String className = actionInvocation.getAction().getClass()
					.getName();
			long startTime = System.currentTimeMillis();
			System.out.println("Before calling action: " + className);
			
			long endTime = System.currentTimeMillis();
			System.out.println("After calling action: " + className
					+ " Time taken: " + (endTime - startTime) + " ms");
			
			User user = (User) session.get("user");
			if (user == null) {
				user = (User) session.get("admin");
				if (user != null) {
					return actionInvocation.invoke();
				}
				return ActionSupport.LOGIN;
			} else if (user != null) {
				return "loggedIn";
			}
			
		} catch (Exception e) { // NOSONAR
			Logger.getLogger(LoginAuthenticationInterceptor.class).error(
					e.toString());
		}
		return null;
	}
	
}
