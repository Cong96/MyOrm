package com.wangcc.entity;

import com.wangcc.annotation.Column;
import com.wangcc.annotation.Entity;

@Entity("user")
public class User {
	private String name;
	@Column("user_id")
	private Integer id;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
