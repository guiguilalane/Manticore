package com.core.loader;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler{

	
	
	private Object instance;
	
	private String target;
	
	public Handler(String s) {
		target = s;
	}
	
	public Object invoke(Object p, Method m, Object[] args) throws Throwable {
		if(instance==null){
			instance = Class.forName(target).newInstance();
		}
		return m.invoke(instance, args);
	}
	
}
