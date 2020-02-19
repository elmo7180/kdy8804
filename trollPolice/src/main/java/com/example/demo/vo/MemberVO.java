package com.example.demo.vo;

public class MemberVO {
	private String EncrypedID; //pk
	private String id;
	private String email;
	private String pwd;
	private String mobile;
	private String nickname;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEncrypedID() {
		return EncrypedID;
	}
	public void setEncrypedID(String encrypedID) {
		EncrypedID = encrypedID;
	}
	
	
	
}
