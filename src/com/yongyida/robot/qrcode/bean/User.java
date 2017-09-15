package com.yongyida.robot.qrcode.bean;

public class User {

	private Long id;
	
	private String name;
	
	private String nickName;

	private Long controller;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getController() {
		return controller;
	}

	public void setController(Long controller) {
		this.controller = controller;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name
				+ ", nickName=" + nickName + ", controller=" + controller + "]";
	}
}
