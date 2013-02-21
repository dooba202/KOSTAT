package com.ibm.query.execute.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;

public class PreparedStatementProxyHandler implements InvocationHandler{

	private PreparedStatement preparedStatement = null;
	
	public PreparedStatementProxyHandler(PreparedStatement realPreparedStatement) {
		this.preparedStatement = realPreparedStatement;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Class<?> targetClass = preparedStatement.getClass();
		
		Method targetMethod = findMethod(targetClass, method);
		if(targetMethod != null){
			try {
				Object result = targetMethod.invoke(preparedStatement, args);
				
				return result;	
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}catch (Exception e) {
				throw e;
			}
		}
		
		return null;
	}

	private Method findMethod(Class<?> targetClass, Method proxyMethod){
		Method[] allMethods = targetClass.getMethods();
		for (Method tmpMethod : allMethods) {
			if(isEquals(tmpMethod, proxyMethod)){
				return tmpMethod;
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isEquals(Method targetMethod, Method proxyMethod) {
		if (targetMethod.getName() == proxyMethod.getName()) {
			if (!targetMethod.getReturnType().equals(proxyMethod.getReturnType()))
				return false;
			/* Avoid unnecessary cloning */
			Class[] params1 = targetMethod.getParameterTypes();
			Class[] params2 = proxyMethod.getParameterTypes();
			if (params1.length == params2.length) {
				for (int i = 0; i < params1.length; i++) {
					if (params1[i] != params2[i])
						return false;
				}
				return true;
			}
		}
		return false;
	}
}
