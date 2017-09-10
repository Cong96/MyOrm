package com.wangcc.config;

import java.util.HashMap;
import java.util.Map;

import com.wangcc.session.ResultMap;

public class Config {
	private DataSource dataSource;
	private Map<String, Class<?>> mapperMap = new HashMap<String, Class<?>>();
	private Map<String, ResultMap> resultMap = new HashMap<String, ResultMap>();

	@Override
	public String toString() {
		return "Config [dataSource=" + dataSource + ", mapperMap=" + mapperMap
				+ ", reslutMap=" + resultMap + "]";
	}

	public void putResultMap(String key, ResultMap value) {
		resultMap.put(key, value);
	}

	public ResultMap getResultmap(String key) {
		return resultMap.get(key);
	}

	public void putMapper(String name, Class<?> clazz) {
		mapperMap.put(name, clazz);

	}

	public Class<?> getMapper(String name) {
		return mapperMap.get(name);
	}

	public Map<String, Class<?>> getMapperMap() {
		return mapperMap;
	}

	public void setMapperMap(Map<String, Class<?>> mapperMap) {
		this.mapperMap = mapperMap;
	}

	public Map<String, ResultMap> getReslutMap() {
		return resultMap;
	}

	public void setReslutMap(Map<String, ResultMap> reslutMap) {
		this.resultMap = reslutMap;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
