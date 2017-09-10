package com.wangcc.config;

public class DataSource {
	@Override
	public String toString() {
		return "DataSource [driverClassName=" + driverClassName + ", url="
				+ url + ", username=" + username + ", password=" + password
				+ "]";
	}

	public String driverClassName;
	public String url;
	public String username;
	public String password;

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
