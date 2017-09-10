package com.wangcc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Column
 * @Description: TODO(ResultMap的属性)
 * @author wangcc
 * @date 2017年9月10日 下午7:16:45
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String value();
}
