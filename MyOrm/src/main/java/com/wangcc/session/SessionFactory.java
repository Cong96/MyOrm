package com.wangcc.session;

import com.wangcc.config.Config;

public interface SessionFactory {
	public Config getConfig();

	public Session openSession();
}
