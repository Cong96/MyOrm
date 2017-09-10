package com.wangcc.session;

import com.wangcc.config.Config;

public class MMSessionFactory implements SessionFactory {
	private final Config config;

	public MMSessionFactory(Config config) {
		this.config = config;
	}

	public Session openSession() {
		// TODO Auto-generated method stub
		return new MMSession(config);
	}

	public Config getConfig() {
		// TODO Auto-generated method stub
		return config;
	}

}
