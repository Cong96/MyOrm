package com.wangcc.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wangcc.annotation.Column;
import com.wangcc.annotation.Entity;
import com.wangcc.mapper.MappedBeanResult;
import com.wangcc.session.MMSessionFactory;
import com.wangcc.session.SessionFactory;
import com.wangcc.util.ClassLoaderWrapper;
import com.wangcc.util.ScanClassUtil;

public class ConfigBuilder {
	private static Config config = new Config();

	private static ClassLoaderWrapper loader = ClassLoaderWrapper.getInstance();

	public static SessionFactory build(String resource) throws IOException {
		parse(resource);
		return new MMSessionFactory(config);
	}

	public static void parse(String resource) {

		try {
			InputStream stream = loader.loadResource(resource);
			SAXReader reader = new SAXReader();
			Document document = reader.read(stream);
			Element root = document.getRootElement();

			evalRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void evalRoot(Element root) throws Exception {
		if (!root.getName().equals("config")) {
			throw new Exception("[config]: root should be <config>");
		}
		evalDataSource(root.element("database"));
		Element mappers = root.element("mappers");
		for (Object item : mappers.elements("mapper"))
			evalMapper((Element) item);
		for (Object item : mappers.elements("ResultMap"))
			evalResultMap((Element) item);
		Element anno = root.element("annotation");
		if (anno != null)
			dealAnnoResultMap(anno);
	}

	public static void evalResultMap(Element node) throws Exception {
		// 映射表名与类名
		String key = node.attributeValue("name");
		String type = node.attributeValue("type");
		if (key == null || type == null)
			throw new Exception("[resultMap]: should contain name and type");
		// 通过反射找到具体类，构建ResultMapper
		Class<?> clazz = loader.loadClass(type);
		MappedBeanResult mapper = new MappedBeanResult(clazz);
		// 映射数据库表属性与类属性
		for (Object item : node.elements("result")) {
			Element i = (Element) item;
			String pValue = i.attributeValue("property");
			String pKey = i.attributeValue("column");
			if (pValue == null)
				throw new Exception(
						"[resultMap]: <result> should contain property and column");
			if (pKey == null)
				pKey = pValue;
			try {
				Field field = clazz.getDeclaredField(pValue);
				mapper.put(pKey, field);
			} catch (NoSuchFieldException e) {
				throw new Exception(String.format(
						"[resultMap]: <property> %s has no such field %s",
						type, pValue));
			}
		}
		config.putResultMap(key, mapper);

	};

	public static void dealAnnoResultMap(Element node) throws Exception {
		String scanPackage = node.attributeValue("scan-package");
		if (scanPackage == null) {
			throw new Exception(
					"element annotation's scan-package can not be null");
		}
		if (scanPackage.indexOf(",") > 0) {
			String[] packageNameArr = scanPackage.split(",");
			for (String packageName : packageNameArr) {
				//
				initAnnoResultMap(packageName);
			}
		} else {
			//
			initAnnoResultMap(scanPackage);
		}
	};

	public static void initAnnoResultMap(String packageName) {
		Set<Class<?>> setClasses = ScanClassUtil.getClasses(packageName);
		for (Class<?> clazz : setClasses) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				Entity entity = clazz.getAnnotation(Entity.class);
				String key = entity.value();
				MappedBeanResult mapper = new MappedBeanResult(clazz);

				if (key.equals("")) {
					String className = clazz.getSimpleName();
					key = className;
				}
				// getFields()只能获取public的字段，包括父类的。
				// 　　而getDeclaredFields()只能获取自己声明的各种字段，包括public，protected，private。
				Field[] fields = clazz.getDeclaredFields();

				for (Field field : fields) {
					String pValue = field.getName();
					if (field.isAnnotationPresent(Column.class)) {
						Column column = field.getAnnotation(Column.class);
						String pKey = column.value();

						if (!pKey.equals("")) {
							pValue = pKey;
						}
					}
					mapper.put(pValue, field);
				}
				config.putResultMap(key, mapper);
			}
		}
	}

	public static void evalMapper(Element node) throws ClassNotFoundException {
		String className = node.attributeValue("class");
		// 使用loadClass比Class.forName 要干净，不会进行初始化操作i，不会允许static 代码块
		Class<?> clazz = loader.loadClass(className);
		String name = clazz.getSimpleName();
		config.putMapper(name, clazz);

	};

	public static void evalDataSource(Element node) throws Exception {
		DataSource data = new DataSource();
		for (Object item : node.elements("property")) {
			Element i = (Element) item;
			String value = getValue(i);
			String name = i.attributeValue("name");
			if (name == null || value == null)
				throw new Exception(
						"[database]: <property> should contain name and value");

			switch (name) {
			case "url":
				data.setUrl(value);
				break;
			case "username":
				data.setUsername(value);
				break;
			case "password":
				data.setPassword(value);
				break;
			case "driverClassName":
				data.setDriverClassName(value);
				break;
			default:
				throw new Exception("[database]: <property> unknown name");
			}
		}
		config.setDataSource(data);
	}

	private static String getValue(Element node) {
		return node.hasContent() ? node.getText() : node
				.attributeValue("value");
	}
}
