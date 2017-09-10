package com.wangcc.session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import com.wangcc.config.Config;
import com.wangcc.config.DataSource;

public class MMSession implements Session {
	private Config config;
	private Connection conn = null;

	// private Map<String,>
	public MMSession(Config config) {
		this.config = config;
		DataSource dataSource = config.getDataSource();
		while (true) {
			try {
				// 如果执行出错，则直接进入catch
				conn = DriverManager.getConnection(dataSource.getUrl(),
						dataSource.getUsername(), dataSource.getPassword());
				// 如果没有出错，但是conn为null，则抛出异常，给catch块处理
				if (conn == null) {
					throw new SQLException();
				} else {
					// 禁止自动提交,并跳出循环
					conn.setAutoCommit(false);
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				try {
					// catch块的操作，一百毫秒以后重连数据库
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
	}

	// 封装Connection的rollback方法
	public void rollback() {
		try {
			conn.rollback();
		} catch (Exception e) {
			// TODO: handle exception
			/*
			 * System.out.println 能重定向到别的输出流，比如输出到txt文本中；
			 * 而System.err.println只能在屏幕上实现打印，即便重定向也一样。
			 * 
			 * http://blog.csdn.net/wangzhengwei2010/article/details/42471087
			 * 缓存的说法存疑 System.out.println有可能在缓存中，由OS和JVM决定是否输出，而System.err.
			 * println它将每一次操作的结果都输出来，这样就很好理解区别了吧。
			 * 在eclipse控制台输出时，System.err.println输出的内容是红色的。
			 * 
			 * 
			 * 对于我们而言在调试程序的时候尽量使用err来输出，这样可以很清晰的定位到任何一个步骤，而out输出的位置顺序很可能和你期望的不一样。
			 */
			System.err.println(e);
		}

	}

	public void commit() {
		if (conn == null)
			return;
		try {
			conn.commit(); // 事务提交
			conn.close();
			// 清空这个Session中的代理类实例
			// cache.clear();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	// _----------封装数据库完成
	// --------------------------封装Mapper，动态代理开始
	// public <T> T getMapper(String name) {
	//
	// };
}
