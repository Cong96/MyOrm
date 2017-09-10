package com.wangcc.session;

import java.sql.ResultSet;

public interface ResultMap {
	enum Type {
		MAP, BEAN, PRIMITIVE
	}

	Type getResultType();

	Object getResult(ResultSet rs, Class<?> returnType) throws Exception;
}
