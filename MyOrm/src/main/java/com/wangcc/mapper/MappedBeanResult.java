package com.wangcc.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import com.wangcc.session.ResultMap;

public class MappedBeanResult implements ResultMap {
	public Map<String, Field> getPropsMap() {
		return propsMap;
	}

	public void setPropsMap(Map<String, Field> propsMap) {
		this.propsMap = propsMap;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	private Map<String, Field> propsMap = new HashMap<>();
	private Class<?> clazz;

	public MappedBeanResult(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void put(String key, Field value) {
		propsMap.put(key, value);
	}

	public Field get(String key) {
		return propsMap.get(key);
	}

	public Object getClassInstance() throws InstantiationException,
			IllegalAccessException {
		return clazz.newInstance();
	}

	@Override
	public Type getResultType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getResult(ResultSet rs, Class<?> returnType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
